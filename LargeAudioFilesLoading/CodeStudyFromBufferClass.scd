//:From source code at PlotView.sc:
+ Buffer {
	plot { |name, bounds, minval, maxval, separately = false|
		var plotter, action;
		if(server.serverRunning.not) { "Server % not running".format(server).warn; ^nil };
		if(numFrames.isNil) { "Buffer not allocated, can't plot data".warn; ^nil };

		plotter = [0].plot(
			name ? "Buffer plot (bufnum: %)".format(this.bufnum),
			bounds, minval: minval, maxval: maxval
		);

		action = { |array, buf|
			var unitStr = if (buf.numChannels == 1) { "samples" } { "frames" };
			{
				plotter.setValue(
					array.unlace(buf.numChannels),
					findSpecs: true,
					refresh: false,
					separately: separately,
					minval: minval,
					maxval: maxval
				);
				plotter.domainSpecs = ControlSpec(0.0, buf.numFrames, units: unitStr);
				// save vertical space with highly multichannel buffer plots
				if(buf.numChannels > 4) { plotter.axisLabelX_(nil) };
			}.defer
		};

		if(server.isLocal) {
			this.loadToFloatArray(action:action)
		} {
			this.getToFloatArray(action:action)
		};

		^plotter
	}
}

//------------------------------------------------------------
+ Buffer {
	loadToFloatArray { arg index = 0, count = -1, action;
		var msg, cond, path, file, array;
		{
			path = PathName.tmp ++ this.hash.asString;
			msg = this.write(path, "aiff", "float", count, index);
			server.sync;
			file = SoundFile.new;
			protect {
				file.openRead(path);
				array = FloatArray.newClear(file.numFrames * file.numChannels);
				file.readData(array);
			} {
				file.close;
				if(File.delete(path).not) { ("Could not delete data file:" + path).warn };
			};

			action.value(array, this);

		}.forkIfNeeded
	}
}