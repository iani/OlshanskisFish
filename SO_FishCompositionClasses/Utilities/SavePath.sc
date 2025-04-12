// Save a path on disc and recall it.
// If saved path is not found on disk,
// open a FileDialog for the user to select a file.
// Since selecting a path with a FileDialog is asynchronous,
// the selected path is always passed through Notification.
// This works the same whether FileDialog is used or not.
//12  4 2025 14:30

SavePath {
	var <>key = \default;
	var <pathFileLocation; 	// the file where tha path is saved
	var <path; // the path that is saved

	*new { | dependant, key = \default |
		// the dependant gets notified with the path when a
		// valid path exists or is chosen by the user.
		if (dependant.respondsTo(\loadPath).not) {
			^postln("Cannot add" + dependant
				+ "because it does not understand loadPath"
			);
		};
		key ?? { key = \default; };
		^this.newCopyArgs(key)
		.readPathFromFile
		.addDependantAction(dependant)
		.checkPath;
	}

	readPathFromFile {
		this.makePathFileLocation;
		if (File.exists(pathFileLocation)) {
			path = File.readAllString(pathFileLocation).standardizePath;
		}{
			path = "";
		}
	}

	makePathFileLocation {
		pathFileLocation = Platform.userAppSupportDir +/+ key.asString ++ ".scd";
	}


	checkPath {
		// if a file exists at the path stored, notify immediatly.
		// else open FileDialog and notify when the user choses a file.
		if ( File.exists(path) ) {
			postln("I will notify path:" + path);
			this.changed(\path, path);
		}{
			this.getPathFromUser;
		}
	}

	getPathFromUser {
		"I will get the path from user".postln;
		FileDialog({ | p |
			path = p.first;
			postln("The path selected was" + path);
			"I will now save this path".postln;
			this.savePath;
			this.changed(\path, path);
		})
	}

	addDependantAction { | dependant |
		postln("I will add this dependant:" + dependant);
		dependant.addNotifier(this, \path, { | n, argPath |
			postln("listener" + n.listener + "gets path" + argPath);
			n.listener.loadPath(argPath);
		});
	}

	savePath {
		postln("I will save the path in" + pathFileLocation);
		File.use(pathFileLocation, "w", {
			|f| f.write(path.standardizePath)
		});
	}
}