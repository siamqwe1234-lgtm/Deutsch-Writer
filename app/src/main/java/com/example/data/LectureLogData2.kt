package com.example.data

object LectureLogData2 {
    val extendedLessons = listOf(
        LectureLog(
            videoNumber = 14,
            title = "Tagesablauf beschreiben (Describing Daily Routine)",
            cefrLevel = "A1",
            keyLearnings = listOf("Learning how to talk about your daily activities, from waking up to going to bed, using separable and reflexive verbs."),
            grammarTopics = listOf("Separable Verbs (Trennbare Verben)", "Time Prepositions"),
            vocabularyList = listOf(
                LessonVocabulary("aufstehen", "ঘুম থেকে ওঠা"),
                LessonVocabulary("frühstücken", "প্রাতঃরাশ করা"),
                LessonVocabulary("duschen", "গোসল করা"),
                LessonVocabulary("einkaufen", "কেনাকাটা করা"),
                LessonVocabulary("fernsehen", "টিভি দেখা")
            ),
            sentenceStructures = listOf(), // Provided dynamically in DB
            insights = listOf("Separable verbs are the most important part of describing a routine."),
            quickRevision = "Prefix moves to the end in separable verbs."
        ),
        LectureLog(
            videoNumber = 15,
            title = "Telling the Time in German (Wie spät ist es?)",
            cefrLevel = "A1",
            keyLearnings = listOf("Learning how to ask for and tell the time in German using formal and informal methods."),
            grammarTopics = listOf("Asking the Time", "Vor vs Nach", "The Halb (Half) Rule", "Viertel (Quarter)"),
            vocabularyList = listOf(
                LessonVocabulary("die Uhrzeit", "সময়"),
                LessonVocabulary("spät", "দেরি"),
                LessonVocabulary("viertel", "এক চতুর্থাংশ (১৫ মিনিট)"),
                LessonVocabulary("halb", "অর্ধেক (৩০ মিনিট)"),
                LessonVocabulary("vor", "বাকি"),
                LessonVocabulary("nach", "বেজে")
            ),
            sentenceStructures = listOf(),
            insights = listOf("Always use the next hour when using halb. (3:30 -> halb vier)."),
            quickRevision = "Es ist halb fünf (4:30)."
        ),
        LectureLog(
            videoNumber = 16,
            title = "Zimmer frei? (Is a room free?)",
            cefrLevel = "A1",
            keyLearnings = listOf("Looking for a room in a shared apartment (WG) and inquiring about availability."),
            grammarTopics = listOf("Ja/Nein Questions (Yes/No Questions)", "The Verb 'Suchen' (To search)"),
            vocabularyList = listOf(
                LessonVocabulary("Das Zimmer", "কক্ষ / ঘর"),
                LessonVocabulary("Frei", "খালি / উন্মুক্ত"),
                LessonVocabulary("Die WG", "শেয়ার্ড অ্যাপার্টমেন্ট"),
                LessonVocabulary("Suchen", "খোঁজা")
            ),
            sentenceStructures = listOf(),
            insights = listOf("Living in a Wohngemeinschaft (WG) is the most common way for students to live in Germany."),
            quickRevision = "Verb moves to the 1st position for Yes/No questions."
        ),
        LectureLog(
            videoNumber = 17,
            title = "Kleidung und Farben (Clothing and Colors)",
            cefrLevel = "A1",
            keyLearnings = listOf("Learning the names of clothing items, various colors, and how to describe what someone is wearing using the verb 'tragen'."),
            grammarTopics = listOf("The Verb 'tragen' (Stem-changing Verb)", "Asking about Colors", "Noun Gender and Articles"),
            vocabularyList = listOf(
                LessonVocabulary("die Kleidung", "পোশাক"),
                LessonVocabulary("die Farbe", "রং"),
                LessonVocabulary("das Hemd", "শার্ট"),
                LessonVocabulary("die Hose", "প্যান্ট"),
                LessonVocabulary("rot", "লাল"),
                LessonVocabulary("blau", "নীল"),
                LessonVocabulary("tragen", "পরিধান করা")
            ),
            sentenceStructures = listOf(),
            insights = listOf("Different genders (der, die, das) for clothing."),
            quickRevision = "tragen changes root vowel to 'ä' for du and er/sie/es."
        ),
        LectureLog(
            videoNumber = 18,
            title = "Woher kommen Sie? (Nicos Weg - A1)",
            cefrLevel = "A1",
            keyLearnings = listOf("Asking and answering about origins using formal language ('Sie')."),
            grammarTopics = listOf("Conjugation of the verb 'kommen'", "Formal 'Sie' vs. Informal 'du'", "The Question Word 'Woher'"),
            vocabularyList = listOf(
                LessonVocabulary("kommen", "আসা"),
                LessonVocabulary("woher", "কোথা থেকে"),
                LessonVocabulary("aus", "থেকে"),
                LessonVocabulary("wer", "কে")
            ),
            sentenceStructures = listOf(),
            insights = listOf("Always use 'Sie' (with a capital 'S') to be polite."),
            quickRevision = "Woher kommen Sie? - Ich komme aus..."
        ),
        LectureLog(
            videoNumber = 19,
            title = "Das Studium (Nicos Weg - A1)",
            cefrLevel = "A1",
            keyLearnings = listOf("Talking about university studies, subjects, and the difference between 'studieren' and 'lernen'."),
            grammarTopics = listOf("'studieren' vs. 'lernen'", "Verb Conjugation (Present Tense)"),
            vocabularyList = listOf(
                LessonVocabulary("studieren", "বিশ্ববিদ্যালয়ে পড়া"),
                LessonVocabulary("lernen", "শেখা / পড়াশোনা করা"),
                LessonVocabulary("das Studium", "উচ্চশিক্ষা"),
                LessonVocabulary("das Fach", "বিষয়"),
                LessonVocabulary("der Student", "ছাত্র (বিশ্ববিদ্যালয়ের)")
            ),
            sentenceStructures = listOf(),
            insights = listOf("Studieren is for University. Lernen is for general learning."),
            quickRevision = "Ich studiere Medizin. Ich lerne Deutsch."
        ),
        LectureLog(
            videoNumber = 20,
            title = "Sätze mit 'weil' | German Subordinate Clauses (Weil)",
            cefrLevel = "A1",
            keyLearnings = listOf("Using the conjunction 'weil' (because) and the resulting word order in subordinate clauses."),
            grammarTopics = listOf("Subordinate Clauses with 'weil' (Nebensätze)"),
            vocabularyList = listOf(
                LessonVocabulary("weil", "কারণ"),
                LessonVocabulary("warum", "কেন"),
                LessonVocabulary("müde", "ক্লান্ত"),
                LessonVocabulary("krank", "অসুস্থ")
            ),
            sentenceStructures = listOf(),
            insights = listOf("In a 'weil' clause, the conjugated verb is pushed to the very end of the sentence."),
            quickRevision = "Ich esse, weil ich Hunger habe."
        ),
        LectureLog(
            videoNumber = 21,
            title = "Die Präpositionen 'aus' und 'von'",
            cefrLevel = "A1",
            keyLearnings = listOf("Understanding the difference between the prepositions 'aus' and 'von' and how to use them with the Dative case."),
            grammarTopics = listOf("The Preposition 'AUS'", "The Preposition 'VON'", "Dative Table Reminder"),
            vocabularyList = listOf(
                LessonVocabulary("kommen", "আসা"),
                LessonVocabulary("die Stadt", "শহর"),
                LessonVocabulary("das Land", "দেশ"),
                LessonVocabulary("das Haus", "বাড়ি")
            ),
            sentenceStructures = listOf(),
            insights = listOf("Aus = origin/inside to outside. Von = starting point/activities."),
            quickRevision = "Both take the Dative case: aus dem Haus, von der Arbeit."
        ),
        LectureLog(
            videoNumber = 22,
            title = "Modal Verbs: müssen (must) & dürfen (may/to be allowed to)",
            cefrLevel = "A1",
            keyLearnings = listOf("Learning how to express obligation, necessity, permission, and prohibition using German modal verbs."),
            grammarTopics = listOf("Conjugation of Modal Verbs", "Sentence Structure (Satzbau)", "Prohibition"),
            vocabularyList = listOf(
                LessonVocabulary("müssen", "অবশ্যই / করতে হবে"),
                LessonVocabulary("dürfen", "অনুমতি পাওয়া / পারা"),
                LessonVocabulary("rauchen", "ধূমপান করা"),
                LessonVocabulary("parken", "পার্ক করা")
            ),
            sentenceStructures = listOf(),
            insights = listOf("The second verb remains in the infinitive form at the very end."),
            quickRevision = "Hier darf man nicht rauchen (forbidden)."
        )
    )
}
