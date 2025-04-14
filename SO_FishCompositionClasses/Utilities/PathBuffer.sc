// 日 13  4 2025 22:27
// Load a buffer using the mechanism of Path / SavePath.
// Notify when buffer is loaded.

PathBuffer : Path {
	var <buffer;

	loadPath { | argPath |
		super.loadPath(argPath);
		buffer = Buffer.read(Server.default, path, action: { | b |
			postln("loaded buffer:" + b);
		})
	}

}