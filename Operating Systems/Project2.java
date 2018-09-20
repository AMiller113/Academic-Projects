
/**
 * Project2.java
 * This project simulates a tanning room. 
 * The room has a manager, a set of tanners, and a set of cleaners. 
 * The tanners can't tan while the room is being cleaned and 
 * the cleaners can't clean while there are tanners in the room. 
 * The manager will act as the synchronization handler and 
 * will signal when the room is open to tanners and locked to cleaners and 
 * will signal when the room is locked to tanners and open to cleaners. 
 * There is no restriction to the number of tanners that can use the room after the first.
 * Assume all cleaners enter and leave at the same time.
 * 
 * @author akeemmiller and chavaglass
 *
 */

class Semaphore{
	private int value;

	public Semaphore(int value) {
		this.value = value;
	}
	
//Added new method to semaphore class that locks the semaphore
	
	public void lock(){
		value = 0;
	}

	public synchronized void w() {
		while (value <= 0) {
			try {
				wait();
			}
			catch (InterruptedException e) { }
		}

		value = 0;
	}

	public synchronized void v() {
		value = 1;
		notify();
	}
}//Semaphore

class TanningRoom {
	
	int tanners = 0;  //Number of tanners in room
	int cleaners = 0;  // Number of cleaners in room
	boolean isClean = true;  //indicates room was cleaned and a tanner has not entered yet
	int cleaningTime = 0; //keeps track of how long its been since last cleaning
	final static int closingTime = 10000000; 
	Semaphore cleanerSem = new Semaphore(0); //controls access to cleaner counter
	Semaphore tannerSem = new Semaphore(0);  //controls access to tanner counter
	int id;
	
	//open the tanning room
	public void start(){
		
		int timeTillClose = 0; //counter to keep track of time
		
		while(timeTillClose<closingTime){
			id = timeTillClose;
			//if the room has not been cleaned in a while 
			if(timeToClean(cleaningTime)){ 
				try {
					Thread managee = new Thread(new manager(id));
					managee.setPriority(Thread.MAX_PRIORITY);;
					managee.start();
					Thread.sleep(1000); //Gives manager extra time to work.
				} catch (InterruptedException e) {}
			}
			//create a new manager thread
			Thread managee = new Thread(new manager(id));
			managee.setPriority(Thread.MAX_PRIORITY); //max priority so that it can control access
			managee.start();
			try {
				Thread.sleep(100); //Gives manager extra time to work.
			} catch (InterruptedException e) {}
			//create a new cleaner thread
			Thread cleaners = new Thread(new cleaner(id));
			cleaners.setPriority(2);
			cleaners.start();
		
			//create a new tanner thread
			Thread tanee = new Thread(new tanner(id));
			tanee.setPriority(Thread.MIN_PRIORITY); 
			tanee.start();
		
			cleaningTime++;
			timeTillClose++;
		}//while
	}//start
	
	public synchronized void increTanners(){
		tanners++;
	}
	
	public synchronized void decreTanners(){
		tanners--;
	}
	
	public synchronized void isCleanFalse(){
		isClean = false;
	}
	
	public synchronized void isCleanTrue(){
		isClean = true;
	}
	
	// check if the room hasn't been cleaned in a while
	public synchronized boolean timeToClean(int time){
		if(time%10000 == 0){
			return true;
		}
		else return false;
	}
	
	public synchronized void increCleaners(){
		cleaners++;
	}
	
	public synchronized void decreCleaners(){
		cleaners--;
	}
	
	
	class tanner implements Runnable{
		
		private int tID;
		
		public tanner(int tID){
			this.tID = tID;
		}
		
		public void run() {	
			if(tanners == 0)
				tannerSem.w();
			
			increTanners();
			
			try {
				System.out.println("Tanner number " + tID +" enters tanning room.");
				Thread.sleep((long) (100*Math.random()));
				
			} catch (InterruptedException e) {;}
		
			if(isClean == true){
				isCleanFalse();
			}
		
			if(tanners != 0){
				decreTanners();
				System.out.println("Tanner number " + tID +" exits tanning room.");
				if(tanners == 0)
					System.out.println("All tanners have exited.");
			}
			
		}
	}//tanner
	
	class cleaner implements Runnable{
		
		private int cID;
		
		public cleaner(int cID){
			this.cID = cID;
		} 
		
		public void run() {
					
			System.out.println("Cleaner number " + cID + " is waiting.");
			cleanerSem.w();
			increCleaners();
			
			try {
				System.out.println("Cleaner number " + cID + " enters tanning room.");
				Thread.sleep(500);
			} catch (InterruptedException e) {}
			
			isCleanTrue();
			decreCleaners();
			System.out.println("Cleaner number " + cID + " exits tanning room.");	
		}
	}//cleaner	
	
	//manager class controls access to the tanning room
	class manager implements Runnable{

		private int mID; //manager ID
		
		public manager(int mID){
			this.mID = mID;
		}
		
		public void run() {
			
			System.out.println("Manager number " + mID + " operating.");
				
			//if the room has not been cleaned recently
			if(timeToClean(cleaningTime) && isClean == false){
				//block any more tanners from entering the room
				tanners = 0;
				tannerSem.lock();
				cleanerSem.v();
				System.out.println("Manager number " + mID + ": Tanners being removed for room cleaning. ");
			}
			
			//if there is a cleaner in the room
			if(cleaners>0){
				cleanerSem.lock(); //do not allow any more cleaners in the room
				System.out.println("Manager number " + mID + ": Cleaners currently in the room, no more cleaners required. ");
				
			}
			
			//if there are not tanners in the room, and the room is not clean
			else if(tanners == 0 && isClean == false){
				tannerSem.lock(); //lock the room to tanners
				cleanerSem.v(); //allow cleaners in
				System.out.println("Manager number " + mID + ": Room is empty. Locking for cleaning. ");
				
			}
			
			// there are no tanners in the room, and the room is clean
			if(tanners == 0 && isClean == true){
				tannerSem.v(); // signal to tanners
				cleanerSem.lock(); // do not allow cleaners in
				System.out.println("Manager number " + mID +": No cleaning being done. Opening to tanners. ");	
			}	
		}//run
		
	}//manager class
	
}

public class Project2 {
	
	public static void main(String[] args) {
	
		//create a TanningRoom and start running
		TanningRoom t1 = new TanningRoom();
		t1.start();
		
	}//main
	
}//Project2

