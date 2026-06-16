const fs = require('fs');

const text = fs.readFileSync('input.txt', 'utf-8');

// Parse logic here
const logs = [];
let currentLog = null;
const lines = text.split('\n');

let parseMode = null; // vocab, grammar, sentences

for(let i = 0; i < lines.length; i++) {
    const line = lines[i].trim();
    if(line.includes("German Learning Video #") || line.includes("Video Number:**")) {
        const match = line.match(/\#?(\d+)/);
        if(match) {
            currentLog = {
                videoNumber: match[1],
                title: "",
                cefrLevel: "A1",
                keyLearnings: [],
                grammarTopics: [],
                vocabularyList: [],
                sentenceStructures: [],
                insights: [],
                quickRevision: ""
            };
            logs.push(currentLog);
        }
    }
    else if(line.includes("**Video Title:**")) {
        if(currentLog) currentLog.title = line.split('**Video Title:**')[1].trim();
    }
    else if(line.includes("### 6. Grammar Points")) {
        parseMode = "grammar";
    }
    else if(line.includes("### 5. Vocabulary List")) {
        parseMode = "vocab";
    }
    else if(line.includes("### 7. Useful Sentences")) {
        parseMode = "sentences";
    }
    else if(line.includes("### 8. Practice Section") || line.includes("### 3.") || line.includes("### 4.")) {
        parseMode = null;
    }
    
    if(parseMode === "grammar" && line.startsWith("* ")) {
        currentLog.grammarTopics.push(line.replace("* ", ""));
    }
    else if(parseMode === "vocab" && line.startsWith("|") && !line.includes("---") && !line.includes("German Word")) {
        const parts = line.split("|").map(s => s.trim());
        if(parts.length >= 4) {
            currentLog.vocabularyList.push({
                word: parts[1].replace(/\*\*/g, ''),
                translation: parts[3], // Bengali
                explanation: parts[2]  // English
            });
        }
    }
    else if(parseMode === "sentences" && line.startsWith("|") && !line.includes("---") && !line.includes("German")) {
        const parts = line.split("|").map(s => s.trim());
        if(parts.length >= 4) {
            currentLog.sentenceStructures.push({
                example: parts[1],
                bengaliMatch: parts[3],
                pattern: parts[2]
            });
        }
    }
}

// Generate Kotlin
let kt = "package com.example.data\n\nobject NewLectureLogData {\n    val lessons = listOf(\n";
for(let log of logs) {
    if(!log || !log.videoNumber || log.title === "") continue;
    kt += `        LectureLog(
            videoNumber = ${log.videoNumber},
            title = "${log.title.replace(/"/g, '\\"')}",
            cefrLevel = "A1",
            keyLearnings = listOf("Imported learning goals."),
            grammarTopics = listOf(
                ${log.grammarTopics.map(g => '"' + g.replace(/"/g, '\\"') + '"').join(',\n                ')}
            ),
            vocabularyList = listOf(
                ${log.vocabularyList.map(v => `LessonVocabulary("${v.word}", "${v.translation.replace(/"/g, '\\"')} (Eng: ${v.explanation.replace(/"/g, '\\"')})")`).join(',\n                ')}
            ),
            sentenceStructures = listOf(
                ${log.sentenceStructures.map(s => `LessonSentence("${s.pattern.replace(/"/g, '\\"')}", "${s.example.replace(/"/g, '\\"')}", "${s.bengaliMatch.replace(/"/g, '\\"')}")`).join(',\n                ')}
            ),
            insights = listOf("Video reference."),
            quickRevision = "Practice the words and sentences."
        ),\n`;
}
kt += "    )\n}\n";

fs.writeFileSync('app/src/main/java/com/example/data/NewLectureLogData.kt', kt);
console.log("SUCCESS");
