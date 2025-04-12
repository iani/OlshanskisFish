// For testing SavePath
PathTest {
	*loadPath { | argPath |
		postln(this.asString + "received loadPath");
		postln("loadPath args are:" + argPath);
	}

	loadPath { | argPath |
		postln(this.asString + "received loadPath");
		postln("loadPath args are:" + argPath);
	}
}

PathTest2 {
	*loadAction { ^PathTest }
}