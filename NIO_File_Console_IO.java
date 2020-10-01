import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;
import java.nio.charset.*;

class NIO_File_Console_IO{
	public static void main(String ar[]){
		Path file = Paths.get("Encoded String.txt");
		try(FileChannel fc = FileChannel.open(file, StandardOpenOption.READ);){

			Charset ch = Charset.forName("IBM737");
			MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
			CharBuffer cb = ch.decode(mbb);
			cb.rewind();
			System.out.println(cb.toString());
		}catch(Exception e){
			System.out.println(e);
		}
	}
}