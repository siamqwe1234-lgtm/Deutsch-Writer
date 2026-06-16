package com.example.data

object NewVocabulary {

    private val rawData = """
1 die Sekunde the second সেকেন্ড
2 die Minute the minute মিনিট
3 die Stunde the hour ঘণ্টা
4 der Tag the day দিন
5 die Woche the week সপ্তাহ
6 das Jahr the year বছর
7 der Wochentag the day of the week সপ্তাহের দিন
8 der Sonntag Sunday রবিবার
9 der Montag Monday সোমবার
10 der Dienstag Tuesday মঙ্গলবার
11 der Mittwoch Wednesday বুধবার
12 der Donnerstag Thursday বৃহস্পতিবার
13 der Freitag Friday শুক্রবার
14 der Samstag Saturday শনিবার
15 das Wochenende the weekend সপ্তাহান্ত (ছুটির দিন)
16 am Wochenende at the weekend সপ্তাহান্তে
17 der Morgen the morning সকাল
18 der Vormittag the forenoon দুপুরের পূর্বভাগ
19 der Mittag the noon দুপুর
20 der Nachmittag the afternoon বিকেল
21 der Abend the evening সন্ধ্যা
22 die Nacht the night রাত
23 der Januar January জানুয়ারী
24 der Februar February ফেব্রুয়ারী
25 der März March মার্চ
26 der April April এপ্রিল
27 der Mai May মে
28 der Juni June জুন
29 der Juli July জুলাই
30 der August August আগস্ট
31 der September September সেপ্টেম্বর
32 der Oktober October অক্টোবর
33 der November November নভেম্বর
34 der Dezember December ডিসেম্বর
35 der Frühling the spring বসন্তকাল
36 der Sommer the summer গ্রীষ্মকাল
37 der Herbst the autumn শরৎকাল
38 der Winter the winter শীতকাল
39 die Farben colours রং
40 schwarz black কালো
41 weiß white সাদা
42 grau grey ধূসর
43 blau blue নীল
44 grün green সবুজ
45 rot red লাল
46 gelb yellow হলুদ
47 braun brown বাদামী
53 Himmelsrichtungen directions দিকসমূহ
54 der Norden the north উত্তর
55 der Süden the south দক্ষিণ
56 der Westen the west পশ্চিম
57 der Osten the east পূর্ব
66 die Adresse the address ঠিকানা
70 das Alter the age বয়স
94 das Apartment apartment অ্যাপার্টমেন্ট / ফ্ল্যাট
95 der Apfel the apple আপেল
98 die Arbeit the work কাজ
100 der Arbeitsplatz job / workplace কর্মক্ষেত্র
102 der Arzt the doctor ডাক্তার
105 die Aufgabe the task কাজ / কর্তব্য
109 der Aufzug the elevator লিফট
110 das Auge the eye চোখ
114 der Ausgang the exit বাহির পথ
115 die Auskunft the information তথ্য / সন্ধান
116 das Ausland abroad বিদেশ
124 der Ausweis the ID card পরিচয়পত্র
126 das Auto the car গাড়ি
131 die Bäckerei the bakery বেকারি
132 das Bad the bath স্নানাগার / গোসলখানা
134 die Bahn the train ট্রেন
135 der Bahnhof the train station রেল স্টেশন
136 der Bahnsteig the platform প্ল্যাটফর্ম
138 der Balkon the balcony বারান্দা / ব্যালকনি
139 die Banane the banana কলা
140 die Bank the bank ব্যাংক
143 der Baum the tree গাছ
156 der Beruf occupation পেশা
163 das Bett the bed বিছানা
165 das Bier the beer বিয়ার
166 das Bild the picture ছবি
183 der Brief the letter চিঠি
186 das Brot the bread পাউরুটি
188 der Bruder the brother ভাই
189 das Buch the book বই
192 der Bus the bus বাস
193 die Butter the butter মাখন
194 das Café the cafe ক্যাফে
196 der Chef the boss প্রধান / বস
198 der Computer the computer কম্পিউটার
206 das Datum the date তারিখ
230 die Ehefrau the wife স্ত্রী
231 der Ehemann the husband স্বামী
232 das Ei the egg ডিম
236 der Eingang the entrance প্রবেশপথ
244 die Eltern the parents বাবা-মা
245 die E-mail the e-mail ইমেইল
260 das Essen the food খাবার
267 die Familie the family পরিবার
282 die Firma the company কোম্পানি
283 der Fisch the fish মাছ
284 die Flasche the bottle বোতল
285 das Fleisch the meat মাংস
289 der Flughafen the airport বিমানবন্দর
290 das Flugzeug the plane বিমান
291 das Formular the form ফরম
292 das Foto the photo ছবি
294 die Frage question প্রশ্ন
295 die Frau the woman / wife নারী / ভদ্রমহিলা
300 der Freund the friend বন্ধু
303 das Frühstück breakfast সকালের নাস্তা
308 der Garten the garden বাগান
309 der Gast the guest অতিথি
319 das Geld the money টাকা / অর্থ
320 das Gemüse the vegetables শাকসবজি
321 das Gepäck the luggage লাগেজ / মালামাল
326 das Geschenk the gift উপহার
328 das Gespräch the conversation কথোপকথন
331 das Getränk the drink পানীয়
334 das Glas the glass গ্লাস
354 das Haar the hair চুল
363 das Handy the cell phone মোবাইল ফোন
364 das Haus the house বাড়ি
365 die Hausaufgabe the homework বাড়ির কাজ
385 das Hotel the hotel হোটেল
386 der Hund the dog কুকুর
403 der Kaffee the coffee কফি
406 die Kartoffel the potato আলু
412 das Kind the child শিশু
414 das Kino the cinema সিনেমা
421 der Koffer the suitcase স্যুটকেস
422 der Kollege the colleague সহকর্মী
425 das Konto the account অ্যাকাউন্ট
426 der Kopf the head মাথা
429 die Küche the kitchen রান্নাঘর
430 der Kuchen the cake কেক
432 der Kühlschrank the fridge রেফ্রিজারেটর
435 der Kunde the customer ক্রেতা / মক্কেল
439 der Laden the shop দোকান
440 das Land the country দেশ
447 das Leben life জীবন
448 die Lebensmittel food / groceries মুদিখানার মালপত্র
451 der Lehrer the teacher শিক্ষক
458 die Leute the people মানুষজন
459 das Licht the light আলো
472 das Mädchen the girl মেয়ে
474 der Mann the man পুরুষ
477 das Meer the sea সমুদ্র
481 der Mensch the person মানুষ
483 die Miete the rent ভাড়া
484 die Milch the milk দুধ
491 die Möbel the furniture আসবাবপত্র
500 die Mutter the mother মা
503 der Name the name নাম
515 das Obst the fruit ফল
521 das Öl the oil তেল
526 das Papier the paper কাগজ
531 der Pass the passport পাসপোর্ট
533 der Plan the plan পরিকল্পনা
535 die Polizei the police পুলিশ
536 die Pommes frites French fries ফ্রেঞ্চ ফ্রাই
537 die Post the post office ডাকঘর
541 der Preis the price দাম / মূল্য
542 das Problem the problem समस्या
544 die Prüfung the exam পরীক্ষা
548 der Raum the room / space কক্ষ / ঘর
549 die Rechnung the bill বিল
553 der Reis the rice চাল / ভাত
560 das Restaurant the restaurant রেস্তোরাঁ
565 der Saft the juice জুস / রস
567 der Salat the salad সালাদ
568 das Salz the salt লবণ
569 Satz sentence বাক্য
581 der Schlüssel the key চাবি
586 der Schrank the cupboard আলমারi
589 die Schule the school স্কুল
590 der Schüler the student ছাত্র
592 die Schwester the sister বোন
594 das Schwimmbad the swimming pool সুইমিং পুল
595 der See the lake হ্রদ
611 das Sofa the sofa সোফা
613 der Sohn the son ছেলে
615 die Sonne the sun সূর্য
618 die Speisekarte the menu মেনু কার্ড
621 die Sprache the language ভাষা
623 die Stadt the city শহর
628 die Straße the street রাস্তা
632 der Student the student বিশ্ববিদ্যালয়ের ছাত্র
636 die Tasche the bag ব্যাগ
637 das Taxi the taxi ট্যাক্সি
638 der Tee the tea চা
642 der Termin the appointment অ্যাপয়েন্টমেন্ট
648 der Tisch the table টেবিল
649 die Tochter the daughter মেয়ে / কন্যা
650 die Toilette the toilet টয়লেট / শৌচাগার
651 die Tomate the tomato টমেটো
654 die Treppe the stairs সিঁড়ি
661 die Uhr the clock ঘড়ি
671 der Urlaub the holiday ছুটি
672 der Vater the father বাবা
682 die Verwandte the relative আত্মীয়
698 das Wasser the water পানি / জল
701 der Wein the wine ওয়াইন
705 die Welt the world পৃথিবী
709 das Wetter the weather আবহাওয়া
717 der Wind the wind বাতাস
724 die Wohnung the apartment ফ্ল্যাট / অ্যাপার্টমেন্ট
726 das Wort the word শব্দ
731 die Zeitung the newspaper সংবাদপত্র
733 das Zimmer the room কক্ষ / ঘর
737 der Zug the train ট্রেন
60 abfahren to leave / depart রওনা হওয়া
63 abholen to pick up তুলে নেওয়া
74 anfangen to start শুরু করা
76 anklicken to click ক্লিক করা
77 ankommen to arrive পৌঁছানো
80 anmachen to turn on চালু করা
81 anmelden to register নিবন্ধন করা
84 anrufen to call ফোন করা
90 antworten to answer উত্তর দেওয়া
93 anziehen to put on পরিধান করা
97 arbeiten to work কাজ করা
106 aufhören to stop থামা
108 aufstehen to get up ঘুম থেকে ওঠা
113 ausfüllen to fill in ফরম পূরণ করা
119 ausmachen to turn off বন্ধ করা
123 aussteigen to get out নেমে যাওয়া
133 baden to bathe গোসল করা
146 beginnen to start / begin শুরু করা
161 bestellen to order অর্ডার করা
162 besuchen to visit বেড়াতে যাওয়া
164 bezahlen to pay পরিশোধ করা
173 bitten to request অনুরোধ করা
175 bleiben to stay থাকা
185 bringen to bring আনা
191 buchstabieren to spell বানান করা
220 drucken to print প্রিন্ট করা
222 drücken to push / press চাপ দেওয়া
227 duschen to shower গোসল করা
237 einkaufen to shop কেনাকাটা করা
238 einladen to invite আমন্ত্রণ জানানো
241 einsteigen to get in / board গাড়িতে ওঠা
248 enden to end শেষ হওয়া
250 entschuldigen to apologize ক্ষমা চাওয়া
254 erklären to explain ব্যাখ্যা করা
255 erlauben to allow অনুমতি দেওয়া
257 erzählen to tell / narrate গল্প বলা
259 essen to eat খাওয়া
262 fahren to drive / ride গাড়ি চালানো
273 feiern to celebrate উদযাপন করা
276 fernsehen to watch TV টিভি দেখা
281 finden to find খুঁজে পাওয়া
286 fliegen to fly উড়ে যাওয়া
293 fragen to ask জিজ্ঞাসা করা
302 frühstücken to eat breakfast সকালের নাস্তা করা
310 geben to give দেওয়া
317 gehen to go / walk যাওয়া
318 gehören to belong অধীনে থাকা
333 gewinnen to win জেতা
335 glauben to believe বিশ্বাস করা
342 gratulieren to congratulate অভিনন্দন জানানো
355 haben to have থাকা / আছে
360 halten to hold / stop থামা / ধরা
369 heiraten to marry বিয়ে করা
370 heißen to be called নামে ডাকা
371 helfen to help সাহায্য করা
383 holen to fetch / get গিয়ে আনা
384 hören to hear / listen শোনা
408 kaufen to buy কেনা
410 kennen to know (someone/somewhere) চেনা / জানা
411 kennenlernen to get to know পরিচিত হওয়া
420 kochen to cook রান্না করা
423 kommen to come আসা
424 können can / to be able to পারা
434 kümmern (sich) to take care of যত্ন নেওয়া
438 lachen to laugh হাসা
444 laufen to walk / run হাঁটা / দৌড়ানো
446 leben to live বেঁচে থাকা
450 legen to lay / put down রাখা
455 lernen to learn শেখা
456 lesen to read পড়া
461 lieben to love ভালোবাসা
465 liegen to lie down শুয়ে থাকা
471 machen to do / make করা / তৈরি করা
482 mieten to rent ভাড়া করা
486 mitbringen to bring along সাথে আনা
487 mitkommen to come along সাথে আসা
489 mitnehmen to take along সাথে নেওয়া
493 mögen to like পছন্দ করা
504 nehmen to take নেওয়া
517 öffnen to open খোলা
546 Rad fahren to cycle সাইকেল চালানো
547 rauchen to smoke ধূমপান করা
551 regnen to rain বৃষ্টি হওয়া
554 reisen to travel ভ্রমণ করা
558 reparieren to repair মেরামত করা
563 riechen to smell ঘ্রাণ নেওয়া
566 sagen to say বলা
573 schicken to send পাঠানো
576 schlafen to sleep ঘুমানো
578 schließen to close বন্ধ করা
582 schmecken to taste স্বাদ লাগা
587 schreiben to write লেখা
593 schwimmen to swim সাঁতার কাটা
596 sehen to see দেখা
609 sitzen to sit বসা
614 sollen should / ought to উচিত
619 spielen to play খেলা করা
622 sprechen to speak কথা বলা
624 stehen to stand দাঁড়ানো
626 stellen to place / put স্থাপন করা
630 studieren to study (at university) উচ্চশিক্ষা গ্রহণ করা
634 suchen to search / look for খোঁজা
635 tanzen to dance নাচ করা
640 telefonieren to make a phone call ফোনে কথা বলা
653 treffen to meet দেখা করা
655 trinken to drink পান করা
657 tun to do করা
660 überweisen to transfer (money) টাকা স্থানান্তর করা
663 umziehen to move / change clothes বাসা বদলানো / কাপড় বদলানো
669 unterschreiben to sign স্বাক্ষর করা
674 verdienen to earn আয় করা
677 verkaufen to sell বিক্রি করা
679 vermieten to rent out ভাড়া দেওয়া
681 verstehen to understand বুঝতে পারা
689 vorstellen (sich) to introduce oneself নিজের পরিচয় দেওয়া
691 wandern to hike পায়ে হেঁটে ভ্রমণ করা
693 warten to wait অপেক্ষা করা
697 waschen to wash ধৌত করা
712 wiederholen to repeat পুনরাবৃত্তি করা
719 wissen to know (a fact) জানা
723 wohnen to reside / live বাস করা
725 wollen to want চাওয়া
728 zahlen to pay পরিশোধ করা
58 ab from / starting at থেকে
59 aber but কিন্তু / তবে
67 allein alone একা
68 also so / therefore তাই / অতএব
69 alt old পুরোনো / বৃদ্ধ
99 arbeitslos unemployed বেকার
101 arm poor গরিব
103 auch also / too ও / ছাড়াও
104 auf on / upon ওপরে
137 bald soon শীঘ্রই
141 bar cash নগদ
148 beide both উভয়ই
152 bekannt known পরিচিত
157 besetzt occupied / busy ব্যস্ত / অধিকৃত
159 besser better আরও ভালো
160 best best সেরা / সর্বোত্তম
167 billig cheap সস্তা
170 bisschen a little একটু / অল্প
171 bitte please দয়া করে
174 bitter bitter তেতো
180 böse angry / bad / wicked রাগান্বিত / দুষ্টু
182 breit wide চওড়া
212 dies this এটি
218 dort there সেখানে
219 draußen outside বাইরে
233 eilig urgent জরুরি
235 einfach simple / easy সহজ
266 falsch wrong ভুল
277 fertig finished / ready প্রস্তুত / শেষ
296 fremd foreign / strange অপরিচিত / বিদেশী
297 frei free / vacant মুক্ত / খালি
301 früher earlier / past পূর্বে / আগে
315 gefallen to like / please পছন্দ হওয়া
322 gerade just / straight এইমাত্র / সোজা
323 geradeaus straight ahead একদম সোজা
324 gern gladly আনন্দের সাথে
336 gleich right away / same এখনই / সমান
339 glücklich happy সুখী
344 groß big / tall বড় / লম্বা
353 gut good ভালো
372 hell bright / light উজ্জ্বল / হালকা
376 heute today আজ
377 hier here এখানে
379 hinten at the back পেছনে
381 hoch high উঁচু
390 immer always সবসময়
395 ja yes হ্যাঁ
398 jetzt now এখন
401 jung young তরুণ
404 kaputt broken / broken down নষ্ট / ভাঙা
416 klar clear / sure স্পষ্ট / অবশ্যই
419 klein small ছোট
437 kurz short / brief সংক্ষিপ্ত
441 lang long লম্বা
443 langsam slowly ধীরে ধীরে
445 laut loud উচ্চ শব্দ
449 ledig single / unmarried অবিবাহিত
452 leicht light / easy হালকা / সহজ
453 leider unfortunately দুর্ভাগ্যবশত
454 leise quiet শান্ত / নিচু স্বরে
460 lieb dear / kind প্রিয় / শান্ত
463 Lieblings... favorite... প্রিয়...
466 links left বাম
470 lustig funny মজাদার
478 mehr more আরও
479 mein my আমার
485 mit with সাথে
494 möglich possible সম্ভব
495 Moment moment মুহূর্ত
497 müde tired ক্লান্ত
502 nächst next পরবর্তী
505 nein no না
506 neu new নতুন
507 nicht not না
508 nichts nothing কিছুই না
509 nie never কখনো না
510 noch still / yet এখনও
511 normal normal স্বাভাবিক
513 nur only কেবল / শুধু
514 oben above / upstairs ওপরে
516 oder or অথবা
519 oft often প্রায়ই
520 ohne without ছাড়া / ব্যতীত
545 pünktlich punctual সময়নিষ্ঠ
550 rechts right ডান
562 richtig correct / right সঠিক
564 ruhig quiet / calm শান্ত
577 schlecht bad খারাপ
583 schnell fast / quickly দ্রুত
584 schon already ইতিমধ্যে
585 schön beautiful সুন্দর
591 schwer heavy / difficult ভারী / কঠিন
598 sehr very খুব
605 selbstständig independent / self-employed স্বাধীন / স্বাবলম্বী
608 Sie you (formal) আপনি
612 sofort immediately অবিলম্বে
616 spät late দেরি
617 später later পরে
644 teuer expensive দামি
652 tot dead মৃত
656 tschüss bye বিদায়
664 und and এবং
665 unser our আমাদের
666 unten below / downstairs নিচে
673 verboten forbidden নিষিদ্ধ
676 verheiratet married বিবাহিত
683 viel much / a lot অনেক
684 vielleicht maybe হয়তো
685 von from / of থেকে / এর
686 vor before / in front of সামনে / আগে
694 warum why কেন
695 was what কী
702 weit far দূরে
703 weiter further আরও
704 welch_ which কোনটি
706 wenig little / few অল্প / কম
707 wer who কে
710 wichtig important গুরুত্বপূর্ণ
711 wie how কেমন / কীভাবে
715 wie viel how much কতটুকু / কত
716 willkommen welcome স্বাগত
718 wir we আমরা
720 wo where কোথায়
721 woher where from কোত্থেকে
722 wohin where to কোন দিকে
727 wunderbar wonderful চমৎকার
729 Zeit time সময়
730 zurzeit at present বর্তমানে
736 zufrieden satisfied সন্তুষ্ট
738 zurück back ফেরত
739 zusammen together একসাথে
740 zwischen between মাঝে / মধ্যবর্তী
"""

    fun getItems(): List<VocabularyItem> {
        val result = mutableListOf<VocabularyItem>()
        val lines = rawData.trim().split("\n")
        
        for (line in lines) {
            if (line.isBlank() || !line[0].isDigit()) continue
            
            val parts = line.split(" ")
            if (parts.size < 4) continue
            
            // Find Bengali start
            var bengaliIndex = -1
            for (i in parts.indices) {
                if (parts[i].any { it in '\u0980'..'\u09FF' }) {
                    bengaliIndex = i
                    break
                }
            }
            
            if (bengaliIndex > 1) {
                val bengaliWord = parts.subList(bengaliIndex, parts.size).joinToString(" ")
                
                // Let's guess where German ends. Let's just say German is index 1, or 1 and 2 if 2 is lowercase.
                // Or simply extract English and German by checking english articles. Actually, we can just split by half.
                // A better approach: 
                // The first word is always German (after the number). Sometimes the second word is also German.
                // Let's find first English word (the, to, a, or just assume the last N-2 words before bengali are English, and first words are German)
                // e.g "die Sekunde the second": parts[1]=""die", parts[2]="Sekunde", parts[3]="the", parts[4]="second"
                // "schwarz black": parts[1]="schwarz", parts[2]="black"
                
                var splitIdx = 2
                if (parts[1].lowercase() in listOf("der", "die", "das", "am", "sich", "rad")) {
                    splitIdx = 3
                    if (parts.size > 3 && parts[2] == "Pommes") splitIdx = 4
                    if (parts.size > 3 && parts[1] == "am" && parts[2] == "Wochenende") splitIdx = 3
                } else if (bengaliIndex > 3 && parts[bengaliIndex - 1] == "employed" && parts[bengaliIndex - 2] == "self-") {
                     // special case self-employed
                }
                
                // Fine tuning split index
                if (parts[splitIdx - 1] == "fahren") splitIdx = 3
                
                // Fallback
                if (splitIdx >= bengaliIndex) splitIdx = bengaliIndex - 1
                
                val german = parts.subList(1, splitIdx).joinToString(" ")
                val english = parts.subList(splitIdx, bengaliIndex).joinToString(" ")
                
                val combinedBengaliEnglish = "${bengaliWord} (${english})"
                
                result.add(
                    VocabularyItem(
                        category = "Everyday Vocabulary",
                        bengaliWord = combinedBengaliEnglish,
                        germanTranslation = german,
                        difficultyLevel = "A1"
                    )
                )
            }
        }
        
        return result
    }
}
