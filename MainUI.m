function varargout = MainUI(varargin)
% MAINUI MATLAB code for MainUI.fig
%      MAINUI, by itself, creates a new MAINUI or raises the existing
%      singleton*.
%
%      H = MAINUI returns the handle to a new MAINUI or the handle to
%      the existing singleton*.
%
%      MAINUI('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in MAINUI.M with the given input arguments.
%
%      MAINUI('Property','Value',...) creates a new MAINUI or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before MainUI_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to MainUI_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help MainUI

% Last Modified by GUIDE v2.5 25-Apr-2016 18:52:09

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @MainUI_OpeningFcn, ...
                   'gui_OutputFcn',  @MainUI_OutputFcn, ...
                   'gui_LayoutFcn',  [] , ...
                   'gui_Callback',   []);
if nargin && ischar(varargin{1})
    gui_State.gui_Callback = str2func(varargin{1});
end

if nargout
    [varargout{1:nargout}] = gui_mainfcn(gui_State, varargin{:});
else
    gui_mainfcn(gui_State, varargin{:});
end
% End initialization code - DO NOT EDIT


% --- Executes just before MainUI is made visible.
function MainUI_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to MainUI (see VARARGIN)
global saveFileName;
saveFileName='Data.mat';

% Choose default command line output for MainUI
handles.output = hObject;

% Update handles structure
guidata(hObject, handles);

% UIWAIT makes MainUI wait for user response (see UIRESUME)
% uiwait(handles.mainForm);


% --- Outputs from this function are returned to the command line.
function varargout = MainUI_OutputFcn(hObject, eventdata, handles) 
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Get default command line output from handles structure
varargout{1} = handles.output;



function pathEdit_Callback(hObject, eventdata, handles)
% hObject    handle to pathEdit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of pathEdit as text
%        str2double(get(hObject,'String')) returns contents of pathEdit as a double


% --- Executes during object creation, after setting all properties.
function pathEdit_CreateFcn(hObject, eventdata, handles)
% hObject    handle to pathEdit (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end
global saveFileName;

load(saveFileName, 'dname');
set(hObject,'String',dname);


% --- Executes on button press in browse.
function browse_Callback(hObject, eventdata, handles)
% hObject    handle to browse (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
global saveFileName;
dname = uigetdir('C:\');
if dname~=0
    set(handles.pathEdit,'String',dname);
    save(saveFileName, 'dname');
end


% --- Executes on button press in windowingButton.
function windowingButton_Callback(hObject, eventdata, handles)
% hObject    handle to windowingButton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
windowing(get(handles.pathEdit,'String'));
set(hObject,'ForegroundColor','green');
