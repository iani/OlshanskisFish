//日 13  4 2025 18:36
//Generic superclass for saving and reloading a path.
//Subclasses specify what to do with the path.

Path {
	var <key = \default; // key for generating the path name
	var <handler; // SavePath instance to handle loading
	var <path;

	*new { | key = \default |
		^this.newCopyArgs(key).makeHandler;
	}

	makeHandler {
		handler = SavePath(this, key);
	}

	*loadPath { | argPath |
		postln(this.asString + "received loadPath");
		postln("loadPath args are:" + argPath);
	}

	loadPath { | argPath |
		postln(this.asString + "received loadPath");
		postln("loadPath args are:" + argPath);
		path = argPath;
	}
}
