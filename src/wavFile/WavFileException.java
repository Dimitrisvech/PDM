package wavFile;


public class WavFileException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8551633801537517243L;

	public WavFileException()
	{
		super();
	}

	public WavFileException(String message)
	{
		super(message);
	}

	public WavFileException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public WavFileException(Throwable cause) 
	{
		super(cause);
	}
}
