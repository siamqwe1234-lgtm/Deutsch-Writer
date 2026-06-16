package com.example.data

data class LessonVocabulary(
    val word: String,
    val translation: String,
    val explanation: String = ""
)

data class LessonSentence(
    val pattern: String,
    val example: String,
    val bengaliMatch: String
)

data class LectureLog(
    val videoNumber: Int,
    val title: String,
    val cefrLevel: String,
    val keyLearnings: List<String>,
    val grammarTopics: List<String>,
    val vocabularyList: List<LessonVocabulary>,
    val sentenceStructures: List<LessonSentence>,
    val insights: List<String>,
    val quickRevision: String
)

object LectureLogData {
    val lessons = listOf(
        LectureLog(
            videoNumber = 1,
            title = "Verb Conjugation and Making Simple Sentences",
            cefrLevel = "A1",
            keyLearnings = listOf(
                "জার্মান ভাষায় ব্যক্তিগত সর্বনাম (Personal Pronouns) এবং তাদের উচ্চারণ কীভাবে করতে হয় তা শিখেছি।",
                "কীভাবে ক্রিয়াপদ বা ভার্ব ব্যবহারের জন্য রুলস এবং কনজুগেশন করতে হয় তার প্রাথমিক ধারণা পেয়েছি।",
                "কীভাবে প্রথম ক্লাসেই খুব সহজে একটি সম্পূর্ণ সঠিক জার্মান বাক্য গঠন করা যায় তার পদ্ধতি সম্পর্কে জেনেছি।"
            ),
            grammarTopics = listOf(
                "Personal Pronouns (ব্যক্তিগত সর্বনাম): বাক্যে কর্তা হিসেবে ব্যবহৃত মৌলিক প্রণাউনগুলোর পরিচিতি।",
                "Verb Conjugation Intro: জার্মান ভাষায় ক্রিয়ার রূপ পরিবর্তন বা কনজুগেশনের প্রাথমিক নিয়ম।"
            ),
            vocabularyList = listOf(
                LessonVocabulary("Ich", "আমি"),
                LessonVocabulary("Du", "তুমি / তুই"),
                LessonVocabulary("Er", "সে (পুংলিঙ্গ)"),
                LessonVocabulary("Wir", "আমরা"),
                LessonVocabulary("Sie / sie", "তারা / আপনি (সম্মানসূচক)")
            ),
            sentenceStructures = listOf(
                LessonSentence(
                    pattern = "Subject + Conjugated Verb + Object",
                    example = "Ich lerne Deutsch",
                    bengaliMatch = "আমি জার্মান শিখি"
                )
            ),
            insights = listOf(
                "জার্মান ভাষার মৌলিক প্রোনাউন যেমন: Ich, Du, Er, Wir, Sie ইত্যাদির সঠিক উচ্চারণ ও অর্থ জানা।",
                "প্রথম দিন থেকেই বাক্য গঠন বা সেন্টেন্স মেকিংয়ের ওপর জোর দেওয়া।",
                "ক্লাসগুলো প্রতি সোম এবং শুক্রবার ১ ঘণ্টার জন্য অনুষ্ঠিত হবে এবং প্র্যাকটিস শিট সলভ করা দরকার।"
            ),
            quickRevision = "Ich (আমি), Du (তুমি), Er (সে), Wir (আমরা), Sie (তারা/আপনি)। জার্মান বাক্যের প্রধান ভিত্তি হলো সাবজেক্ট অনুযায়ী ভার্বের সঠিক রূপ বা কনজুগেশন ব্যবহার করা।"
        ),
        LectureLog(
            videoNumber = 2,
            title = "German Alphabet and Pronunciation",
            cefrLevel = "A1",
            keyLearnings = listOf(
                "জার্মান বর্ণমালার (Alphabets) প্রতিটি অক্ষরের সঠিক উচ্চারণ ও তাদের ধ্বনিগত বৈশিষ্ট্য শিখেছি।",
                "কীভাবে একটি সেশনেই প্রায় ৯৫% জার্মান শব্দের সঠিক উচ্চারণ নিজে নিজেই করা সম্ভব, তার কিছু দারুণ নিয়ম শিখেছি।",
                "প্রথম সেশনে শেখা প্রোনাউন ও ক্রিয়াপদ বা ভার্ব কনজুগেশনের (Verb Conjugation) নিয়মগুলো আরও একবার ঝালাই করে নিয়েছি।"
            ),
            grammarTopics = listOf(
                "Revision of Verb Conjugation: জার্মান ভাষার সব ভার্ব সাধারণত \"en\" দিয়ে শেষ হয়।",
                "ভার্ব রুট অনুযায়ী কনজুগেশন যুক্ত করার নিয়ম: Ich (-e), Du (-st), Er (-t), Wir (-en), Sie (-en)।",
                "Phonetics and Letters: জার্মান বর্ণমালার স্বতন্ত্র উচ্চারণ রীতি ও তার ব্যাকরণগত ব্যবহার।"
            ),
            vocabularyList = listOf(
                LessonVocabulary("trinken", "পান করা", "রুট শব্দ: trink-"),
                LessonVocabulary("Ich trinke", "আমি পান করি"),
                LessonVocabulary("Du trinkst", "তুমি পান করো"),
                LessonVocabulary("Er trinkt", "সে পান করে"),
                LessonVocabulary("Wir trinken", "আমরা পান করি"),
                LessonVocabulary("Sie trinken", "আপনি পান করেন / তারা পান করে")
            ),
            sentenceStructures = listOf(
                LessonSentence(
                    pattern = "Subject + Conjugated Verb ('en' suffix replaced)",
                    example = "Ich trinke Wasser.",
                    bengaliMatch = "আমি জল পান করি।"
                )
            ),
            insights = listOf(
                "জার্মান ভাষার প্রথম ৪-৫টি সেশন সবচেয়ে গুরুত্বপূর্ণ, কারণ এখানে ভাষার মূল ভিত্তি তৈরি হয়।",
                "সঠিক উচ্চারণের নিয়ম জানা থাকলে প্রথম দিনেই ৯৫% জার্মান শব্দ নির্ভুলভাবে পড়ার যোগ্যতা অর্জন করা যায়।"
            ),
            quickRevision = "ভার্ব কনজুগেশনের মূল মন্ত্র: Ich -e, Du -st, Er -t, Wir -en, Sie -en। জার্মান অক্ষরের উচ্চারণ ইংরেজি থেকে কিছুটা ভিন্ন এবং তা অত্যন্ত নিয়মাবদ্ধ।"
        ),
        LectureLog(
            videoNumber = 3,
            title = "German Numbers (জার্মান সংখ্যা)",
            cefrLevel = "A1",
            keyLearnings = listOf(
                "জার্মান ভাষায় ১ থেকে শুরু করে মিলিয়ন-বিলিয়ন পর্যন্ত সংখ্যা কীভাবে অতি সহজে মাত্র ১৭টি মৌলিক শব্দ মনে রেখে গঠন করা যায় তা শিখেছি।",
                "বড় সংখ্যাগুলোকে ভেঙে ভেঙে প্লেসহোল্ডার (যেমন- Hundert, Tausend) বসিয়ে জার্মান নিয়মে বলার কৌশল জেনেছি।",
                "জার্মান ভাষায় সময় দেখার প্রাথমিক নিয়মাবলি শিখেছি।"
            ),
            grammarTopics = listOf(
                "The \"And\" (und) Rule for Numbers: ২১ থেকে ৯৯ পর্যন্ত সংখ্যার ক্ষেত্রে প্রথমে একক ঘর (Ones), মাঝে \"und\", এবং শেষে দশকের ঘর (Tens) বসে। যেমন: ২১ = einundzwanzig।",
                "Teens Rule (১৩-১৯): এককের সাথে সরাসরি ১০ (zehn) যুক্ত করতে হয়। যেমন: ১৩ = dreizehn।",
                "Tens Rule (৩০-৯০): দশকের সংখ্যাগুলোর শেষে \"-zig\" (উচ্চারণ: জিশ/sish) যুক্ত করতে হয়।"
            ),
            vocabularyList = listOf(
                LessonVocabulary("Null", "0"),
                LessonVocabulary("Eins / Ein", "1 / সংখ্যা গঠনে Ein"),
                LessonVocabulary("Zwei", "2"),
                LessonVocabulary("Drei", "3"),
                LessonVocabulary("Vier", "4"),
                LessonVocabulary("Fünf", "5"),
                LessonVocabulary("Sechs", "6"),
                LessonVocabulary("Sieben", "7"),
                LessonVocabulary("Acht", "8"),
                LessonVocabulary("Neun", "9"),
                LessonVocabulary("Zehn", "10"),
                LessonVocabulary("Elf", "11"),
                LessonVocabulary("Zwölf", "12"),
                LessonVocabulary("Zwanzig", "20"),
                LessonVocabulary("Hundert", "100"),
                LessonVocabulary("Tausend", "1000"),
                LessonVocabulary("und", "এবং"),
                LessonVocabulary("Uhr", "ঘড়ি / টা (সময় প্রকাশের জন্য)")
            ),
            sentenceStructures = listOf(
                LessonSentence(
                    pattern = "[Ones] + und + zwanzig",
                    example = "Einundzwanzig (21)",
                    bengaliMatch = "একুশ (এক এবং বিশ)"
                ),
                LessonSentence(
                    pattern = "[Number] + tausend + [Number] + hundert + [Tens/Ones]",
                    example = "Viertausenddreihunderteinundzwanzig (4321)",
                    bengaliMatch = "চার হাজার তিনশত একুশ"
                ),
                LessonSentence(
                    pattern = "[Hour] + Uhr + [Minutes]",
                    example = "Elf Uhr vierzig (11:40)",
                    bengaliMatch = "এগারোটা চল্লিশ মিনিটে"
                )
            ),
            insights = listOf(
                "জার্মান সংখ্যা পদ্ধতি প্রথম প্রথম কঠিন মনে হলেও এটি অত্যন্ত নিয়মাবদ্ধ এবং মাত্র ১৭টি শব্দ দিয়ে যেকোনো সংখ্যা তৈরি সম্ভব।",
                "ইংরেজিতে আগে Tens এবং পরে Ones বলা হলেও জার্মানে আমাদের স্থানীয় ভাষার মতো আগে Ones এবং পরে Tens বলা হয়।"
            ),
            quickRevision = "২১ থেকে ৯৯ = [Ones] + und + [Tens]। বড় সংখ্যার ক্ষেত্রে সিকোয়েন্স: Tausend (হাজার) → Hundert (শতক) → Ones (একক) + und + Tens (দশক)।"
        ),
        LectureLog(
            videoNumber = 4,
            title = "Self-Introduction and Base Revision",
            cefrLevel = "A1",
            keyLearnings = listOf(
                "জার্মান ভাষায় নিজের সম্পূর্ণ পরিচয় (Self-Introduction) দেওয়ার পদ্ধতি এবং প্রয়োজনীয় বাক্যসমূহ শিখেছি।",
                "সংখ্যা গণনার (Numbers 1-100) ক্ষেত্রে সাধারণত শিক্ষার্থীরা যে ভুলগুলো করে থাকে এবং সেগুলো এড়ানোর উপায় সম্পর্কে জেনেছি।"
            ),
            grammarTopics = listOf(
                "Personal Pronouns & Conjugation Review: বাক্য গঠনের জন্য বেসিক কন্ডিশন রিভিশন।",
                "Preposition for Introduction: দেশের নামের পূর্বে 'aus' (থেকে) এবং শহরের নামের পূর্বে 'in' (মধ্যে/এ) প্রিপজিশন ব্যবহারের সঠিক নিয়ম।"
            ),
            vocabularyList = listOf(
                LessonVocabulary("Zwanzig", "২০"),
                LessonVocabulary("Dreißig", "৩০"),
                LessonVocabulary("Achtunddreißig", "৩৮"),
                LessonVocabulary("Tschüss", "বিদায় (Casual Bye)")
            ),
            sentenceStructures = listOf(
                LessonSentence(
                    pattern = "Ich komme aus + [Country]",
                    example = "Ich komme aus Bangladesch.",
                    bengaliMatch = "আমি বাংলাদেশ থেকে এসেছি।"
                ),
                LessonSentence(
                    pattern = "Ich wohne in + [City]",
                    example = "Ich wohne in Dhaka.",
                    bengaliMatch = "আমি ঢাকায় থাকি।"
                )
            ),
            insights = listOf(
                "নিজের পরিচয় দেওয়ার সময় ৩টি বেসিক প্রশ্নের উত্তর জানা আবশ্যক: আপনি কে, কোথা থেকে এসেছেন এবং কোথায় থাকেন।",
                "`kommen` ভার্বের সাথে সর্বদা `aus` এবং `wohnen` ভার্বের সাথে সর্বদা `in` প্রিপজিশন বসে।"
            ),
            quickRevision = "সংখ্যা কাউন্টিং ট্রিক: 38 = 8 (Acht) + und + 30 (dreißig) = achtunddreißig। 'Ich komme aus...' দেশের নামের ক্ষেত্রে এবং 'Ich wohne in...' শহরের ক্ষেত্রে।"
        ),
        LectureLog(
            videoNumber = 5,
            title = "Modal Verbs in German",
            cefrLevel = "A1",
            keyLearnings = listOf(
                "জার্মান ভাষায় মোডাল ভার্বস (Modal Verbs) কী এবং কীভাবে এগুলো মনের ভাব বা ইমোশন প্রকাশ করতে সাহায্য করে সে সম্পর্কে জেনেছি।",
                "বাক্যে মোডাল ভার্ব ব্যবহারের নিয়ম এবং এর ফলে মূল ভার্বের (Main Verb) কী পরিবর্তন ঘটে তা শিখেছি।"
            ),
            grammarTopics = listOf(
                "Modal Verbs Position & Sentence Structure: বাক্যে মোডাল ভার্ব সবসময় ২য় পজিশনে বসে এবং কনজুগেশন হয় শুধু মোডাল ভার্বটির।",
                "মূল অ্যাকশন ভার্বটি (Main Verb) কোনো পরিবর্তন ছাড়াই ইনফিনিটিভ (Infinitive) রূপে একদম বাক্যের শেষে চলে যায়।"
            ),
            vocabularyList = listOf(
                LessonVocabulary("können", "পারা (Can)"),
                LessonVocabulary("müssen", "অবশ্যই করা/বাধ্যবাধকতা (Must)"),
                LessonVocabulary("sollen", "উচিত (Should)"),
                LessonVocabulary("wollen", "চাওয়া (Want to)"),
                LessonVocabulary("möchten", "মৃদু ইচ্ছা প্রকাশ করা (Would like to)"),
                LessonVocabulary("dürfen", "অনুমতি থাকা (May/Allowed to)"),
                LessonVocabulary("lernen", "শেখা (Learn)"),
                LessonVocabulary("lesen", "পড়া (Read)")
            ),
            sentenceStructures = listOf(
                LessonSentence(
                    pattern = "Subject + Modal Verb (Conjugated) + Object + Main Verb (Infinitive)",
                    example = "Wir können Deutsch lernen.",
                    bengaliMatch = "আমরা জার্মান শিখতে পারি।"
                ),
                LessonSentence(
                    pattern = "Subject + Modal Verb (Conjugated) + Object + Main Verb (Infinitive)",
                    example = "Wir müssen Pizza kochen.",
                    bengaliMatch = "আমাদের অবশ্যই পিৎজা রান্না করতে হবে।"
                )
            ),
            insights = listOf(
                "মোডাল ভার্ব ব্যবহারের সময় বাক্যে একটি মাত্র ভার্বই কনজুগেটেড বা রূপান্তরিত হয়।",
                "জার্মান সংস্কৃতিতে ইচ্ছা প্রকাশের ক্ষেত্রে 'möchten' (would like to) ব্যবহার করাকে বেশি ভদ্র ও সমাদৃত মনে করা হয়।"
            ),
            quickRevision = "কুইক স্ট্রাকচার রুল: Wir + [Modal Verb] + ..... + [Main Verb]. যেমন: Wir müssen Deutsch lernen (আমাদের অবশ্যই জার্মান শিখতে হবে)।"
        ),
        LectureLog(
            videoNumber = 6,
            title = "Future Tense (Futur I) and Modal Verbs Continuation",
            cefrLevel = "A1",
            keyLearnings = listOf(
                "জার্মান ভাষায় কীভাবে 'werden' সাহায্যকারী ভার্ব (Auxiliary Verb) ব্যবহার করে ভবিষ্যৎ কাল বা Future Tense-এর বাক্য তৈরি করতে হয় তা শিখেছি।",
                "মোডাল ভার্বগুলোর সাথে অন্যান্য পার্সোনাল প্রোনাউন (Ich, Du, Er/Sie/Es) যুক্ত করে বাক্য গঠনের এডভান্সড নিয়ম আয়ত্ত করেছি।"
            ),
            grammarTopics = listOf(
                "Future Tense Structure (Futur I): জার্মান ফিউচার টেন্স গঠনে \"werden\" ভার্বটি ২য় পজিশনে বসে সাবজেক্ট অনুযায়ী কনজুগেট বা পরিবর্তিত হয়। মূল অ্যাকশন ভার্বটি অপরিবর্তিত (Infinitive) অবস্থায় বাক্যের শেষে চলে যায়।",
                "werden-এর কনজুগেশন: Ich werde, Du wirst, Er/Sie/Es wird, Wir werden, Ihr werdet, Sie/sie werden।"
            ),
            vocabularyList = listOf(
                LessonVocabulary("werden", "হওয়া / করা (Will/Shall - সাহায্যকারী ভার্ব হিসেবে)"),
                LessonVocabulary("morgen", "আগামীকাল (Tomorrow)"),
                LessonVocabulary("nächstes Jahr", "আগামী বছর (Next year)"),
                LessonVocabulary("machen", "করা (To do / make)"),
                LessonVocabulary("gehen", "যাওয়া (To go)"),
                LessonVocabulary("kaufen", "কেনা (To buy)")
            ),
            sentenceStructures = listOf(
                LessonSentence(
                    pattern = "Subject + werden + Object/Time + Infinitive Verb",
                    example = "Wir werden morgen Deutsch lernen.",
                    bengaliMatch = "আমরা আগামীকাল জার্মান শিখব।"
                ),
                LessonSentence(
                    pattern = "Subject + werden + Object + Infinitive Verb",
                    example = "Ich werde ein Auto kaufen.",
                    bengaliMatch = "আমি একটি গাড়ি কিনব।"
                )
            ),
            insights = listOf(
                "জার্মান ফিউচার টেন্সের বাক্য গঠন মোডাল ভার্বের বাক্য গঠনের নিয়মের মতোই সমান্তরাল।",
                "বাক্যের ২য় পজিশনে 'werden' বসলে এবং শেষে মূল ভার্ব থাকলে তা ভবিষ্যৎ নির্দেশ করে।"
            ),
            quickRevision = "ফিউচার টেন্স চেনার সহজ উপায়: বাক্যে werden + একদম শেষে Infinitive Verb দেখলেই বুঝবেন এটি ভবিষ্যৎ কাল।"
        ),
        LectureLog(
            videoNumber = 7,
            title = "W-Questions and Continuations",
            cefrLevel = "A1",
            keyLearnings = listOf(
                "জার্মান ভাষায় 'W' দিয়ে প্রশ্নবোধক শব্দগুলোর ব্যবহার শিখেছি (যেমন: wer, was, wo, woher ইত্যাদি) ।",
                "অপরিচিত ও বয়োজ্যেষ্ঠদের সাথে সম্মানসূচক \"Sie\" এবং সমবয়সী বা বন্ধুদের সাথে সাধারণ \"du\" প্রোনাউন ব্যবহারের নিয়মাবলী জেনেছি।",
                "জার্মান ভাষায় কন্টিনিউয়াস টেন্সের জন্য আলাদা কোনো সহায়ক ভার্ব নেই, সেটি সাধারণ বর্তমান কাল দিয়েই প্রকাশ করতে হয়।"
            ),
            grammarTopics = listOf(
                "W-Fragen Structure: W-Word ১ম পজিশনে, Conjugated Verb ২য় পজিশনে, এবং Subject ৩য় পজিশনে বসে।",
                "Formal \"Sie\" vs. Informal \"du\": পরিস্থিতি ও সম্পর্ক অনুযায়ী সঠিক প্রশ্ন ও বাক্যের রূপ রূপান্তর।"
            ),
            vocabularyList = listOf(
                LessonVocabulary("wer", "কে (who)"),
                LessonVocabulary("was", "কি (what)"),
                LessonVocabulary("wo", "কোথায় (where)"),
                LessonVocabulary("woher", "কোথা থেকে (where from)"),
                LessonVocabulary("wie", "কেমন / কীভাবে (how)")
            ),
            sentenceStructures = listOf(
                LessonSentence(
                    pattern = "W-Word + Conjugated Verb + Subject + ...?",
                    example = "Woher kommen Sie?",
                    bengaliMatch = "আপনি কোথা থেকে এসেছেন?"
                ),
                LessonSentence(
                    pattern = "W-Word + Conjugated Verb + Subject + ...?",
                    example = "Was machst du?",
                    bengaliMatch = "তুমি কি করছ?"
                )
            ),
            insights = listOf(
                "জার্মান প্রশ্ন গঠনে Verb-এর স্থান সবসময় ২য় পজিশনে নির্দিষ্ট থাকে (W-Questions-এর ক্ষেত্রে)।",
                "ভার্বের সাধারণ রূপ অপরিবর্তিত রেখে কাল গঠন করা জার্মান ব্যাকরণের অন্যতম বড় সুবিধা।"
            ),
            quickRevision = "জার্মানে W-Fragen-এ Verb ২য় পজিশনে বসে। সম্মানসূচক Sie-এর সাথে Verb-এর শেষে en থাকে এবং এটি বড় হাতের S দিয়ে লেখা হয়।"
        ),
        LectureLog(
            videoNumber = 8,
            title = "Requests in German and Yes-No Questions",
            cefrLevel = "A1",
            keyLearnings = listOf(
                "জার্মান ভাষায় কীভাবে হ্যাঁ/না বোধক প্রশ্ন (Yes/No Questions) তৈরি করতে হয় সে সম্পর্কে বিস্তারিত শিখেছি।",
                "কীভাবে খুব সহজে মোডাল ভার্ব (Modal Verbs) ব্যবহার করে বিনম্রভাবে অনুরোধ বা পারমিশন চাওয়া (Requests in German) যায় তা শিখেছি।"
            ),
            grammarTopics = listOf(
                "Ja/Nein-Fragen (Yes/No Questions): এই ধরণের প্রশ্নে কোনো 'W' শব্দ থাকে না। বাক্য গঠনের ক্ষেত্রে Verb সবসময় ১ম পজিশনে বসে এবং Subject ২য় পজিশনে বসে।",
                "Requests with Modal Verbs: বিনম্র অনুরোধ বা পারমিশন চাইতে মোডাল ভার্ব (যেমন: dürfen, können) ১ম পজিশনে বসে এবং মূল ভার্বটি ইনফিনিটিভ রূপে বাক্যের শেষে বসে।",
                "Complex W-Fragen Position: 'Welch' (Which) এবং 'Wie viel' (How many) এর সাথে সংযুক্ত নাউনটি একত্রে ১ম পজিশন গঠন করে। ২য় পজিশনে Verb বসে।"
            ),
            vocabularyList = listOf(
                LessonVocabulary("ja / nein", "হ্যাঁ / না"),
                LessonVocabulary("mir", "আমাকে (Me / To me)"),
                LessonVocabulary("geben", "দেওয়া (To give)"),
                LessonVocabulary("welch", "কোনটি (Which)"),
                LessonVocabulary("wie viel", "কতগুলো (How many)"),
                LessonVocabulary("Auto / Autos", "গাড়ি / গাড়িগুলো")
            ),
            sentenceStructures = listOf(
                LessonSentence(
                    pattern = "Verb + Subject + Object?",
                    example = "Spielst du Tennis?",
                    bengaliMatch = "তুমি কি টেনিস খেলো?"
                ),
                LessonSentence(
                    pattern = "Darf + Subject + Object + Infinitive Verb?",
                    example = "Darf ich Harry Potter lesen?",
                    bengaliMatch = "আমি কি হ্যারি পটার পড়তে পারি?"
                ),
                LessonSentence(
                    pattern = "Können + Subject + mir + Object + geben?",
                    example = "Können Sie mir Pizza geben?",
                    bengaliMatch = "আপনি কি আমাকে পিৎজা দিতে পারেন?"
                )
            ),
            insights = listOf(
                "হ্যাঁ/না প্রশ্নের উত্তর সবসময় 'Ja' অথবা 'Nein' দিয়ে শুরু হয়।",
                "অনুরোধ বা হ্যাঁ/না প্রশ্নের ক্ষেত্রে জার্মান ব্যাকরণে এক্সেপশন ঘটে, অর্থাৎ এই দুই ক্ষেত্রে Verb সবসময় ১ম পজিশনে বসে।",
                "Welch / Wie viel এর সাথে নাউনটি আলাদা করা যায় না, এরা পুরো ফ্রেজ হিসেবে ১ম পজিশনে থাকে।"
            ),
            quickRevision = "অনুরোধের সহজ নিয়ম: নিজের জন্য অনুমতি চাইলে 'Darf ich...' এবং অন্যকে বিনম্রভাবে দেওয়ার অনুরোধ করতে 'Können Sie mir... geben?' ব্যবহার করুন।"
        ),
        LectureLog(
            videoNumber = 9,
            title = "Golden Words, Greetings, and Essential Vocabulary",
            cefrLevel = "A1",
            keyLearnings = listOf(
                "জার্মান ভাষায় দৈনন্দিন যোগাযোগের অত্যন্ত গুরুত্বপূর্ণ কিছু সামাজিক শব্দ (যেমন: ধন্যবাদ, দয়া করে, দুঃখিত) এবং সম্ভাষণের ব্যবহার শিখেছি।",
                "দিনের বিভিন্ন সময়ে কীভাবে সম্ভাষণ জানাতে হয় এবং বিদায় নিতে হয় তা বিস্তারিত জেনেছি।"
            ),
            grammarTopics = listOf(
                "Adjective Endings in Greetings: কেন 'Guten Morgen' (পুংলিঙ্গ) বনাম 'Gute Nacht' (স্ত্রীলিঙ্গ) ব্যবহৃত হয় তার জেন্ডার ভিত্তিক প্রাথমিক ধারণা।",
                "Word-to-Word Translation Avoidance: ইংরেজি বা বাংলা থেকে সরাসরি অনুবাদের গোলকধাঁধা এড়ানো এবং চিন্তার ভাবপ্রকাশ করা।"
            ),
            vocabularyList = listOf(
                LessonVocabulary("Danke", "ধন্যবাদ"),
                LessonVocabulary("Bitte", "দয়া করে / স্বাগত (You're welcome)"),
                LessonVocabulary("Guten Morgen", "शुभ সকাল (Good morning)"),
                LessonVocabulary("Guten Tag", "শুভ দিন / নমস্কার (Good day)"),
                LessonVocabulary("Guten Abend", "শুভ সন্ধ্যা (Good evening)"),
                LessonVocabulary("Gute Nacht", "শুভ রাত্রি (Good night)"),
                LessonVocabulary("Tschüss", "বিদায় (Bye - Informal)"),
                LessonVocabulary("Auf Wiedersehen", "আবার দেখা হবে (Goodbye - Formal)")
            ),
            sentenceStructures = listOf(
                LessonSentence(
                    pattern = "Wie geht es + [Pronoun Constraint]?",
                    example = "Wie geht es Ihnen?",
                    bengaliMatch = "আপনি কেমন আছেন? (Formal)"
                ),
                LessonSentence(
                    pattern = "Wie geht's + ...?",
                    example = "Wie geht es dir?",
                    bengaliMatch = "তুমি কেমন আছ? (Informal)"
                )
            ),
            insights = listOf(
                "social manners এর মূল ভিত্তি হলো 'Danke' এবং 'Bitte' এর নির্বিঘ্ন ব্যবহার।",
                "Morgen, Tag, Abend এই নাউনগুলো পুংলিঙ্গ হওয়ায় এদের আগে 'Guten' বসে, কিন্তু Nacht স্ত্রীলিঙ্গ হওয়ায় 'Gute' বসে।"
            ),
            quickRevision = "শুভ রাত্রির বিদায় সম্ভাষণে সর্বদা 'Gute Nacht' বলুন, বাকি গ্রিটিংস বা সম্ভাষণের ক্ষেত্রে 'Guten Morgen/Tag/Abend' ব্যবহার করুন।"
        ),
        LectureLog(
            videoNumber = 10,
            title = "Introduction to German Cases (Kasus) - Part 1",
            cefrLevel = "A1",
            keyLearnings = listOf(
                "জার্মান ভাষা শিক্ষার অন্যতম গুরুত্বপূর্ণ অধ্যায় 'কেস বা কারক' (Cases)-এর মৌলিক ধারণার সাথে পরিচিত হয়েছি।",
                "জানতে পেরেছি কীভাবে বাক্যে কোনো নাউনের ভূমিকা অনুযায়ী তার আর্টিকেল (der, die, das) পরিবর্তিত হয়ে যায়।"
            ),
            grammarTopics = listOf(
                "The Concept of Cases (কারক পরিচয়): জার্মান ভাষায় ৪টি কেস বা কারক রয়েছে।",
                "Nominative (কর্তৃকারক): বাক্যের মূল কর্তা বা সাবজেক্ট (Subject), যে কাজটি সম্পাদন করে।",
                "Accusative (কর্মকারক): বাক্যের ডিরেক্ট অবজেক্ট (Direct Object), যার ওপর সরাসরি ক্রিয়া সম্পাদিত হয়।",
                "Dative (সম্প্রদান কারক): বাক্যের ইনডিরেক্ট অবজেক্ট (Indirect Object), যার উদ্দেশ্যে বা যার জন্য কাজটি করা হয়।"
            ),
            vocabularyList = listOf(
                LessonVocabulary("Der Ball", "বল (The ball)")
            ),
            sentenceStructures = listOf(
                LessonSentence(
                    pattern = "Subject (Nominative) + Verb + Indirect Object (Dative) + Direct Object (Accusative)",
                    example = "Ich gebe dem Mann einen Ball.",
                    bengaliMatch = "আমি লোকটিকে একটি বল দিচ্ছি।"
                )
            ),
            insights = listOf(
                "নাউনের জেন্ডার (der/die/das) জানা ছাড়া কেস বা কারক সঠিকভাবে প্রয়োগ করা পুরোপুরি অসম্ভব।",
                "ইংরেজি ভাষাভাষীদের জন্য কেস বোঝা কিছুটা কঠিন হলেও বাংলা বা ভারতীয় ভাষার মাতৃভাষাবাসীদের জন্য কারকের গভীর মিল থাকায় এটি শেখা অনেক সহজ।"
            ),
            quickRevision = "Nominative হলো সাবজেক্ট, Accusative ডিরেক্ট অবজেক্ট, এবং Dative ইনডিরেক্ট অবজেক্ট। কেসের ওপর নির্ভর করে আর্টিকেলের রূপ বদলাবে (যেমন der পরিবর্তিত হয়ে den বা dem হয়)।"
        ),
        LectureLog(
            videoNumber = 11,
            title = "Cases in German: Part 2 (Possessive & Indefinite)",
            cefrLevel = "A1",
            keyLearnings = listOf(
                "পজেসিভ প্রোনাউন (আমার, তোমার) এবং ইনডেফিনিট আর্টিকেল (একটি)-এর রূপান্তর বা ডিক্লেনশন নিয়ে বিস্তারিত আলোচনা করা হয়েছে।",
                "কিভাবে কোনো কিছু না মুখস্থ করে সরাসরি ম্যাথমেটিক্যাল বা ট্রিকি উপায়ে (Step-by-step method) জটিল কেস টেবিলগুলো খুব সহজে প্রয়োগ করা যায় তা শিখেছি।"
            ),
            grammarTopics = listOf(
                "Indefinite Article Table ('Ein' টেবিল): ডেফিনিট আর্টিকেলের সাথে মিল রেখে ইনডেফিনিট আর্টিকেলের ডিক্লেনশন ব্যবহার।",
                "Possessive Pronouns: 'আমার' অর্থে mein এবং 'তোমার' অর্থে dein এর রূপ পরিবর্তন।",
                "The Step-by-Step Trick (A Replacement Method): My বা Your যুক্ত বাক্যকে নির্ভুলভাবে গঠন করতে প্রথমে My/Your-কে 'A' (একটি) দিয়ে প্রতিস্থাপন করে আর্টিকেল নির্বাচন করুন, তারপর 'ein'-এর জায়গায় 'mein/dein' বসিয়ে দিন।"
            ),
            vocabularyList = listOf(
                LessonVocabulary("Kuli / Kugelschreiber", "কলম (Pen - Masculine)"),
                LessonVocabulary("Tasche", "ব্যাগ (Bag - Feminine)"),
                LessonVocabulary("Buch", "বই (Book - Neutral)"),
                LessonVocabulary("mein", "আমার (My)"),
                LessonVocabulary("dein", "তোমার (Your)")
            ),
            sentenceStructures = listOf(
                LessonSentence(
                    pattern = "Subject + Verb + Accusative Possessive Pronoun + Noun",
                    example = "Ich habe meinen Kuli.",
                    bengaliMatch = "আমার কাছে আমার কলমটি আছে। (Accusative Masculine - meinen)"
                ),
                LessonSentence(
                    pattern = "Subject + Verb + mit (Dative) + Possessive Pronoun + Noun",
                    example = "Du spielst mit deinem Auto.",
                    bengaliMatch = "তুমি তোমার গাড়ির সাথে খেলছ। (Dative Neutral - deinem)"
                )
            ),
            insights = listOf(
                "আলাদা করে জটিল ডিক্লেনশন চার্ট মুখস্থ করার কোনো প্রয়োজন নেই, যদি 'Ein' টেবিলটি ভালোভাবে আয়ত্ত করা যায়।",
                "শব্দের সঠিক জেন্ডার এবং বাক্যে নাউনের কেস (Nom, Acc, Dat) মিলেই পুরো ফর্মুলাটি গঠিত হয়।"
            ),
            quickRevision = "Accusative Masculine-এ 'ein' হয়ে যায় 'einen' এবং dative masculine/neutral-এ 'ein' হয়ে যায় 'einem'। পজেসিভস গঠনে ট্রিকটি মনে রাখুন: My/Your ➔ 'A' দিয়ে প্রতিস্থাপন ➔ আর্টিকেলের এন্ডিং যুক্ত করা!"
        ),
        LectureLog(
            videoNumber = 12,
            title = "Cases: Part 3 (Possessive Expansion & 20 Prepositions)",
            cefrLevel = "A1",
            keyLearnings = listOf(
                "পজেসিভ প্রোনাউনের সম্প্রসারণ (তাঁর/ওর - sein, আমাদের - unser, তাদের/ওর - ihr, আপনার - Ihr) সম্পর্কে শিখেছি।",
                "জার্মান ব্যাকরণে কেস অনুযায়ী ৩টি ভিন্ন ক্যাটাগরির মোট ২০টি গুরুত্বপূর্ণ প্রেপজিশন (Prepositions) সম্পর্কে বিস্তারিত অবগত হয়েছি।"
            ),
            grammarTopics = listOf(
                "Possessive Pronouns Expansion: sein, unser, ihr, Ihr এর রূপের কেস ভিত্তিক পরিবর্তন।",
                "Dative Prepositions Table: mit, nach, zu, aus, bei, seit, von। এগুলোর পরের নাউনটি সবসময় Dative কেসে হবে।",
                "Accusative Prepositions Table: für, ohne, bis, durch, gegen, um। এগুলো বাক্যে থাকলে পরবর্তী নাউনটি Accusative হবে।",
                "Local Prepositions Intro: an, auf, über, unter, vor, hinter, in, neben, zwischen এর সঠিক ধারণা। মুখের ও হাতের কিছু শারীরিক অঙ্গভঙ্গির ট্রিক দিয়ে পজিশন চেনার জাদুকরী টেকনিক।"
            ),
            vocabularyList = listOf(
                LessonVocabulary("sein", "তাঁহার / ওর (His)"),
                LessonVocabulary("unser", "আমাদের (Our)"),
                LessonVocabulary("ihr / Ihr", "তাঁর (Her) / তাদের (Their) / আপনার (Formal)"),
                LessonVocabulary("nach", "পরে (after)"),
                LessonVocabulary("zu", "প্রতি / দিকে (to)"),
                LessonVocabulary("für", "জন্য (for)"),
                LessonVocabulary("ohne", "ছাড়া (without)")
            ),
            sentenceStructures = listOf(
                LessonSentence(
                    pattern = "... + mit (Dative) + Possessive Pronoun + Feminine Noun",
                    example = "Wir kommen mit unserer Oma.",
                    bengaliMatch = "আমরা আমাদের দাদীর সাথে আসছি। (unserer)"
                ),
                LessonSentence(
                    pattern = "Subject + Verb + Accusative Possessive Pronoun + Feminine Noun",
                    example = "Er liebt seine Uhr.",
                    bengaliMatch = "সে তার ঘড়িটি ভালোবাসে। (seine)"
                )
            ),
            insights = listOf(
                "জার্মান ভাষায় 'On' বোঝাতে স্পর্শের ক্ষেত্রে an বা auf এবং ওপরে শুন্যস্থানে থাকলে über ব্যবহৃত হয়।",
                "কোনো প্রিপজিশন ব্যবহার করার সাথে সাথেই বাক্যের পরবর্তী আর্টিকেলটি স্বয়ংক্রিয়ভাবে তার নির্দিষ্ট কেসে পরিণত হয়।"
            ),
            quickRevision = "স্মল 'ihr' = Her / Their। ক্যাপিটাল 'Ihr' = Your (Formal)। 'mit' থাকলে সর্বদা Dative হবে, 'für' থাকলে সর্বদা Accusative হবে।"
        ),
        LectureLog(
            videoNumber = 13,
            title = "Prepositions and Revision Masterclass",
            cefrLevel = "A1 / A2",
            keyLearnings = listOf(
                "জার্মান কেস ও ২০টি প্রিপজিশনের চূড়ান্ত ব্যবহার এবং সেগুলোর রূপান্তর নিয়ে মাস্টারক্লাস রিভিশন সম্পন্ন করেছি।",
                "জ জড় বা জীব বস্তুকে পুংলিঙ্গ (der), স্ত্রীলিঙ্গ (die) বা ক্লীবলিঙ্গ (das) জেন্ডার হিসেবে ভাবার পদ্ধতি পুনর্বিবেচনা করেছি।"
            ),
            grammarTopics = listOf(
                "German Cases Summary: Nominative (Subject), Accusative (Direct Object), Dative (Indirect Object) এর কুইক কনসেপ্ট রিভিশন।",
                "Article Declension Chart Application: চার্টের সঠিক row ও column ইন্টারসেকশনের মাধ্যমে নির্ভুল আর্টিকেল নির্ধারণের নিয়ম।"
            ),
            vocabularyList = listOf(
                LessonVocabulary("der Hund", "কুকুর (The male dog)"),
                LessonVocabulary("die Katze", "বিড়াল (The female cat)")
            ),
            sentenceStructures = listOf(
                LessonSentence(
                    pattern = "Simple Present = Continuous meaning",
                    example = "Ich koche für meine Mutter.",
                    bengaliMatch = "আমি আমার মায়ের জন্য রান্না করছি / রান্না করি।"
                )
            ),
            insights = listOf(
                "জার্মান ভাষায় কন্টিনিউয়াস টেন্সের জন্য আলাদা কোনো অক্সিলিয়ারি ভার্ব বা আইএনজি যুক্ত রূপ নেই; সাধারণ বর্তমান কালই সেই অর্থ বহন করে।",
                "নিখুঁত জার্মান বাক্য গঠনে ডিক্লেনশন চার্টে সঠিক এন্ডিং ব্যবহারের বিকল্প নেই।"
            ),
            quickRevision = "জার্মান টেন্স অ্যালার্ট: 'Ich koche' মানে 'আমি রান্না করি' এবং 'আমি রান্না করছি' উভয়ই। কন্টিনিউয়াস টেন্সের জন্য আলাদা কোনো সাহায্যকারী ক্রিয়াপদ ব্যবহারের ভুল করবেন না।"
        )
    )
}
