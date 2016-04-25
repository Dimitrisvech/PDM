function [] = windowing(path)
%% window and transform all wav files in directory
%clear all
%% load directory and files with .wav extension
%path = 'c:\\temp\';
files = dir(fullfile(path,'*.wav'));

%% audioread all files into 'database'
wavDB = cell(length(files),3);
for i=1:length(files)
    [y,Fs] = audioread(fullfile(path,files(i).name));
    wavDB{i,1} = y;
    wavDB{i,2} = Fs;
    
    %get severity of disease from filename
    [pathstr, name, ext] = fileparts(files(i).name);
    C = strsplit(name);
    severity_label = sscanf(C{2}, '%*c%d', [1 inf]);
    wavDB{i,3} = severity_label;
end
clear pathstr name ext;

%% window all files in database - create windowed database
[lx, ly] = size(wavDB);
winDB = cell(lx,2);
for i=1:lx %for every file in wavDB...
    millisecs = 20; %size of window in millisecs
    winSize = round((wavDB{i,2}/1000)*millisecs); %size of window in samples
    
    loc = 1;
    ind = 1;

    samvec = zeros(2*round(length(wavDB{i,1})/winSize), winSize + 1); %1 for label, not needed at this point
    
    % cut the file INTO PIECES this is OUR LAST RESORT
    while loc+winSize <= (length(wavDB{i,1})-winSize)
        samvec(ind , 1:winSize) = wavDB{i,1}(loc:(loc+winSize-1));
         loc = loc + winSize/2; %have 50% overlap
         ind = ind + 1;
    end
    samvec( ~any(samvec,2), : ) = []; %remove zero rows
    winDB{i,1} = samvec(:,1:winSize);
    winDB{i,2} = wavDB{i,3}; %insert severity label, per-file...
                             %... rather than per-window
    
end
clear samvec;

%% write window database into files - 1 per audiofile
tranPath = fullfile(path,'windowed');
mkdir(path,'windowed');

for i=1:length(winDB)
    %temppath = strcat(tranPath,'\',files(i).name,'.mat');
    temppath = fullfile(tranPath,strcat(files(i).name,'.mat'));
    tempmat = winDB{i};
    save(temppath, 'tempmat');
end
%save(strcat(windowPath,'\','something.mat'), 'winDB');
clear tempmat;

%% do lotsa wavelet transforms
cont_or_disc = 'continuous'; %choose type of transform

% get total number of windows, for creating database
total_size = 0; 
[sizex, sizey] = size(winDB);
for i=1:sizex
    [lenx leny] = size(winDB{i});
    total_size = total_size + lenx;
end

tranDB = cell(total_size, 2); %spot for transform and label
dbIndex = 1; %current index
tranPath = fullfile(path,'transformed\samples\');
mkdir(path,'transformed\samples');

wl = 'db4'; %wavelet type
sc = 5; %resolution (2^sc)

if  strcmp( cont_or_disc , 'continuous' )
    for i=1:length(winDB)
        [lenx, leny]=size(winDB{i,1});
        mkdir(path,fullfile('transformed\samples\',int2str(winDB{i,2})));
        for j=1:lenx
            ccfs = cwt(winDB{i,1}(j,:),1:2^sc,wl);
            tranPathC = fullfile(tranPath,int2str(winDB{i,2}),'\',strcat(int2str(dbIndex),'.tiff'));
            %imwrite(im2uint16(mat2gray(ccfs,[-0.1, 0.1])), tranPathC, 'tiff');
            imwrite(im2uint16(mat2gray(ccfs)), tranPathC, 'tiff');
            % -0.1, 0.1 are arbitrary 'black' & 'white' so that all transforms
            % not sure which is the better way to do it... don't delete the
            % comments before we ask Alex
            dbIndex = dbIndex + 1;
        end
    end
else %discrete  
    for i=1:length(winDB)
        [lenx, leny]=size(winDB{i,1});
        mkdir(path,fullfile('transformed\samples\',int2str(winDB{i,2})));
        for j=1:lenx
            [c,l]=wavedec(winDB{i,1}(j,:),sc,wl);
            % Compute and reshape DWT to compare with CWT.
            cfd=zeros(sc,leny);
            for k=1:sc
                d=detcoef(c,l,k);
                d=d(ones(1,2^k),:);
                cfd(k,:)=wkeep(d(:)',leny);
            end
            cfd=cfd(:);
            I=find(abs(cfd) <sqrt(eps));
            cfd(I)=zeros(size(I));
            cfd=reshape(cfd,sc,leny);

            %tranPathD = strcat(tranPath,int2str(winDB{i,2}),'\',int2str(dbIndex),'.tiff');
            tranPathD = fullfile(tranPath,int2str(winDB{i,2}),strcat(int2str(dbIndex),'.tiff'));
            %imwrite(im2uint16(mat2gray(cfd,[-0.1, 0.1])), tranPathD, 'tiff');
            imwrite(im2uint16(mat2gray(cfd)), tranPathD, 'tiff');
            dbIndex = dbIndex + 1;
        end
    end
end
end
%% save labels database into file - uneeded
%tranPath = strcat(path,'transformed\labels');
% mkdir(path,'transformed');
%save(tranPath, 'labels');

%% create imageDataStore for CNN
% do this in a separate file, as it's not specifically relevant to..
% the transformation
% the following is how to create the input data of the CNN (for
% training), including labels
% imds = ImageDatastore('c:\temp\transformed\samples',...
%   'IncludeSubfolders', true, 'FileExtentions', '.tiff',...
%   'LabelSource','foldernames');