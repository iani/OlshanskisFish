#!/usr/bin/env fishery

import wave
import struct
import csv
import sys

def wav_to_csv(input_wav, output_csv):
    try:
        with wave.open(input_wav, 'rb') as wav_file:
            n_channels = wav_file.getnchannels()
            sample_width = wav_file.getsampwidth()
            n_frames = wav_file.getnframes()
            sample_rate = wav_file.getframerate()
            audio_data = wav_file.readframes(n_frames)

            if n_channels != 1:
                raise ValueError("This program only supports mono (1-channel) WAV files.")

            # Determine format for struct unpacking
            if sample_width == 1:
                fmt = f"{n_frames}B"  # unsigned char for 8-bit
            elif sample_width == 2:
                fmt = f"{n_frames}h"  # signed short for 16-bit
            elif sample_width == 4:
                fmt = f"{n_frames}i"  # signed int for 32-bit
            else:
                raise ValueError(f"Unsupported sample width: {sample_width} bytes")

            samples = struct.unpack(fmt, audio_data)

            # Write samples to CSV
            with open(output_csv, 'w', newline='') as csv_file:
                writer = csv.writer(csv_file)
                writer.writerow(["SampleIndex", "Amplitude"])
                for i, sample in enumerate(samples):
                    writer.writerow([i, sample])

            print(f"Successfully written {n_frames} samples to {output_csv}")

    except Exception as e:
        print(f"Error processing file: {e}")

# Example usage
if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python wav_to_csv.py input.wav output.csv")
    else:
        input_wav = sys.argv[1]
        output_csv = sys.argv[2]
        wav_to_csv(input_wav, output_csv)

# How to Use:
# Save the script to a file, e.g., wav_to_csv.py.

# Run from the terminal:

# bash
# Copy
# Edit
# python wav_to_csv.py input.wav output.csv
# Let
