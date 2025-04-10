#!/usr/bin/env fishery

import wave
import numpy as np
import csv

def wav_to_csv(wav_file, csv_file):
    # Open the WAV file
    with wave.open(wav_file, 'rb') as wav:
        # Get parameters
        n_channels = wav.getnchannels()
        n_samples = wav.getnframes()
        sample_rate = wav.getframerate()

        # Read frames and convert to numpy array
        frames = wav.readframes(n_samples)
        samples = np.frombuffer(frames, dtype=np.int16)  # Assuming 16-bit PCM

        # Check if audio is single-channel
        if n_channels != 1:
            raise ValueError("Audio file must be single channel.")

    # Write to CSV
    with open(csv_file, 'w', newline='') as csvfile:
        csv_writer = csv.writer(csvfile)
        csv_writer.writerow(samples)

    print(f"Successfully converted {wav_file} to {csv_file}.")

# Example usage
wav_to_csv('input.wav', 'output.csv')

# Install NumPy: If you haven't already, install NumPy using pip:
pip install numpy

# Save the Code: Copy the code into a Python file, e.g., wav_to_csv.py.

# Run the Program: Execute the program and provide the input WAV file name:

python wav_to_csv.py
