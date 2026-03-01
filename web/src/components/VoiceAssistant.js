import React, { useState } from 'react';
import * as speechCommands from '@tensorflow-models/speech-commands';

const VoiceAssistant = () => {
  const [isListening, setIsListening] = useState(false);
  const [transcript, setTranscript] = useState("");

  const startListening = async () => {
    const recognizer = speechCommands.create('BROWSER_FFT');
    await recognizer.ensureModelLoaded();
    recognizer.listen(
      ({ scores }) => {
        const result = scores[scores.indexOf(Math.max(...scores))];
        setTranscript(result);
        processCommand(result);
      },
      { includeSpectrogram: true, probabilityThreshold: 0.75 }
    );
    setIsListening(true);
  };

  const processCommand = (command) => {
    console.log("Processing command:", command);
    // TODO: Send to backend or process locally
  };

  return (
    <div>
      <button onClick={startListening} disabled={isListening}>
        {isListening ? "Listening..." : "🎤 Speak"}
      </button>
      <p>You said: <strong>{transcript}</strong></p>
    </div>
  );
};

export default VoiceAssistant;