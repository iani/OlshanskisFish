//:Testing the limits of reading large audio files into sclang / scsynth
//
FileDialog({ | x |
	postln("loading:" + x.first);
	Buffer.read(Server.default, x.first, action: { | b |
		b.postln;
		b.numFrames.postln;
		{ b.plot }.defer(1);

	})
});