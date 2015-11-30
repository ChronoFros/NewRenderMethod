package MortalClient;

import java.io.DataInputStream;
import java.io.IOException;

import NewRenderMethod.NewRenderMethod;

public class Input implements Runnable{

	DataInputStream in;
	NewRenderMethod client;
	
	public Input(DataInputStream in, NewRenderMethod client){
		this.in=in;
		this.client=client;
	}

	@Override
	public void run() {
		while(true){
			try{
				int PlayerID = in.readInt();
				int x = in.readInt();
				int y = in.readInt();
				boolean ONKEY = in.readBoolean();
				
				boolean KEYUP = in.readBoolean();
				boolean KEYDOWN = in.readBoolean();
				boolean KEYLEFT = in.readBoolean();
				boolean KEYRIGHT = in.readBoolean();
				client.UpdateChords(PlayerID, x, y, ONKEY, KEYUP, KEYDOWN, KEYLEFT, KEYRIGHT);
			}catch(IOException e){
				e.printStackTrace();
			}
			
		}
	}
}
