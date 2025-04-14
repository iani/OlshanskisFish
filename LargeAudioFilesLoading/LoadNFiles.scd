~load = { | num = 2 |
	var root, pathname, folder, filebase, newpath;
	var arrays, reader, buffer;
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
				buffer = Buffer.read(Server.default, newpath);
				0.5.wait;
				arrays add: buffer;
			}{
				"Skipping non existent file path".postln;
			}
		};
		"Finished importing all data".postln;
	}.fork;
	arrays;
};
//:
b = ~load.(50);
//:
b[4].play;
b[3].play;
b[2].play;

b[0].play;

b[0].dur;
//:
b[0].bufnum;
//:playing a single buffer buffers with PlayBuf
p = { | bufnum |
	PlayBuf.ar(1, bufnum, BufRateScale.kr(bufnum), doneAction: Done.freeSelf)
};
//:
{
	p.(b[0].bufnum)
}.play;
//:
//:playing multiple buffers with PlayBuf
m = { | bnums |
	var src;
	src;
	src = bnums collect: { | bufnum |
		PlayBuf.ar(1, bufnum, BufRateScale.kr(bufnum),
			doneAction: Done.freeSelf
		);
	};
	Mix(src);
};
//:
{
	var bufs;
	bufs = [0, 1, 2] collect: b[_];
	m.(bufs);
}.play;
//:
{
	var bufs;
	bufs = (0..49) collect: b[_];
	m.(bufs);
}.play;
//:
b[0].sampleRate;
//:64 channels - each on a semitone?
(midinote: (30..93).pseq, dur: 0.1) +> \test;