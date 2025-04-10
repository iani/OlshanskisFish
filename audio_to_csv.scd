//From poe / claude 3.5
//:Load a WAV file into a Buffer
//
var buffer = Buffer.read(s, "path/to/your/file.wav");

// Check if the buffer is loaded
buffer.sendMsg;

// Convert Buffer to Signal array
var signal = Signal.new(buffer.numFrames);

// Fill the Signal with the buffer data
buffer.getn(signal, 0, buffer.numFrames);

// Use the Signal in your synthesis or processing
signal;

//:interactive version with file dialog:
f = { | path |
	var buffer, signal;
	var bufsize = 1000;

	buffer = Buffer.read(Server.default, path.first, action: { | b |
		b.getn(0, bufsize, {|d| d.postln; ~signal = d });
	});
};

FileDialog({ | x |
	f.(x)
});

//:
~signal;
~signal.plot;