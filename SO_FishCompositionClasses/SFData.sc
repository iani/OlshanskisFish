//:木 10  4 2025 20:19
//Load large audio files into data.
//Do some useful stuff with them such as plot,
//Sonify in various waves,
//Analyse.
//Export as csv files, etc.
SFData {
	var <path, <buffer; // free the buffer after loading it in data
	var <data; //  FloatArray containing the sound file's sample data

	*open { // open with user dialog
		// Returns a Ref containing the instance created after
		// the user chooses a path.
		var new;
		new = Ref();
		FileDialog({ | p |
			new.value = this.newCopyArgs(p.first).read;
		});
		^new;
	}

	read {
		this.loadToFloatArray;
	}

	loadToFloatArray { arg index = 0, count = -1, action;
		var msg, cond, tempath, file, array;
		{
			file = SoundFile.new;
			protect {
				file.openRead(path);
				array = FloatArray.newClear(
					file.numFrames * file.numChannels
				);
				file.readData(array);
				data = array;

			} {
				file.close;
				// if(File.delete(path).not) {
				// 	("Could not delete data file:" + path).warn
				// };
			};

			// action.value(array, this);

		}.forkIfNeeded
	}

	loadToFloatArrayServer { arg index = 0, count = -1, action;
		// Obsolete method from Buffer.
		var msg, cond, tempath, file, array;
		{
			buffer = Buffer.read(Server.default, path);
			Server.default.sync;
			tempath = PathName.tmp ++ this.hash.asString;
			msg = buffer.write(tempath, "aiff", "float", count, index);
			Server.default.sync;
			file = SoundFile.new;
			protect {
				file.openRead(tempath);
				array = FloatArray.newClear(
					file.numFrames * file.numChannels
				);
				file.readData(array);
				data = array;

			} {
				file.close;
				if(File.delete(path).not) {
					("Could not delete data file:" + path).warn
				};
			};

			// action.value(array, this);

		}.forkIfNeeded
	}

	// loading multiple files.
	*openn { | num = 2 |
		var root, pathname, folder, filebase, newpath;
		var arrays, reader;
		root =  "/Users/iani/Documents/Projects/MARES_MasterCourseHonorato202501ff/Mentees/004-barinova-osbanova/Data/fishwavdata/channel_1.wav";
		// File.exists(root).postln;
		pathname = PathName(root);
		folder = pathname.pathOnly;
		// folder.postln;
		filebase = pathname.fileNameWithoutDoubleExtension;
		// filebase.postln;
		filebase = filebase[..7];
		// filebase.postln;
		newpath = (folder +/+ filebase ++ 1.asString ++ ".wav"); // .postln;
		// File.exists(newpath).postln;
		arrays = List();
		{
			(1..num) do: { | n |
				postln("Reading" + n);
				newpath = (folder +/+ filebase ++ n.asString ++ ".wav").postln;
				if (File.exists(newpath)) {
					reader = this.newCopyArgs(newpath);
					reader.read;
					0.5.wait;
					arrays add: reader.data;
				}{
					"Skipping non existent file path".postln;
				}
			};
			"Finished importing all data".postln;
		}.fork;
		^arrays;
	}
}