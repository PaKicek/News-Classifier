package org.pakicek.model;

import java.util.Arrays;
import java.util.List;

/**
 * Enum representing news topics/categories
 */
public enum Topic {
    POLITICS("Politics",
            Arrays.asList("полити", "politics", "politic", "государств", "government",
                    "власть", "power", "правительств", "administration", "выбор",
                    "elections", "election", "парламент", "parliament", "президент",
                    "president", "senate", "congress", "diplomacy", "foreign policy",
                    "росси", "украин", "сша", "russia", "ukrain", "usa",
                    "кремл", "kremlin", "путин", "putin", "лавр", "lavrov", "госдум", "песков", "peskov",
                    "сво", "nato", "нато", "мид", "санкц", "санкт", "саммит", "summit", "форум",
                    "встреч", "meeting", "переговор", "talks", "диплома", "международ", "international",
                    "войск", "troops", "army", "воен", "military", "конфликт", "conflict", "мирн", "peace",
                    "суверен", "sovereign", "территори", "territory", "евросоюз", "eu", "ес", "европарламент",
                    "законопроект", "bill", "закон", "law", "власт", "authorities", "посол", "ambassador",
                    "заявление", "statement", "визит", "visit", "протокол", "protocol", "соглашен", "agreement",
                    "коалиц", "coalition", "оппозиц", "opposition", "стратег", "strategy", "резолюц", "resolution",
                    "петиц", "petition", "лобб", "lobby", "идеолог", "ideology", "субсид", "subsidy", "грант", "grant",
                    "миротвор", "peacekeeping", "посреднич", "mediation", "эмбарго", "embargo", "протекц", "protectionism")),

    ECONOMICS("Economics",
            Arrays.asList("экономи", "economics", "economy", "бизнес", "business",
                    "финанс", "finance", "рынок", "market", "деньги", "money", "транспорт", "aвтомобил", "transport",
                    "инвестици", "investment", "банк", "bank", "currency", "промышленн", "менедж",
                    "stock", "stock market", "trading", "inflation", "unemployment", "недвижим",
                    "кредит", "loan", "credit", "ипотек", "mortgage", "заем", "дефицит", "deficit", "бюджет", "budget",
                    "налог", "tax", "сбор", "fee", "пошлин", "duty", "тариф", "tariff", "инфляц", "курс", "exchange rate",
                    "рубл", "ruble", "доллар", "dollar", "евро", "euro", "валюта", "сбербанк", "sberbank", "втб", "vtb",
                    "цб", "central bank", "банкир", "banker", "актив", "asset", "облигац", "bond", "акц", "share",
                    "фонд", "fund", "бирж", "exchange", "трейд", "trade", "импорт", "import", "экспорт", "export",
                    "таможн", "customs", "логистик", "logistics", "поставк", "supply", "сделк", "deal", "контракт", "contract",
                    "прибыл", "profit", "убыток", "loss", "доход", "revenue", "income", "расход", "expense", "издержк", "cost",
                    "оборот", "turnover", "лизинг", "leasing", "франшиз", "franchise", "рента", "rent", "аренд", "lease",
                    "ликвид", "liquidity", "санац", "rehabilitation", "банкрот", "bankruptcy", "долг", "debt",
                    "заработн", "wage", "salary", "компенсац", "compensation", "преми", "bonus", "дивиденд", "dividend",
                    "кэшбэк", "cashback", "скидк", "discount", "акц", "promotion", "маркетинг", "marketing", "бренд", "brand",
                    "конкурен", "competition", "монопол", "monopoly", "олигопол", "oligopoly", "лиценз", "license", "патент", "patent",
                    "роялти", "royalty", "ноу-хау", "know-how", "нематериальн", "intangible")),

    SOCIETY("Society",
            Arrays.asList("общество", "society", "социальн", "social", "люди",
                    "people", "семья", "family", "образовани", "education",
                    "дети", "children", "пенси", "pension", "community",
                    "demographics", "population", "welfare", "social issues",
                    "вуз", "university", "студент", "student", "школ", "school", "учител", "teacher", "абитуриент", "entrant",
                    "поступлен", "admission", "стипенд", "scholarship", "грант", "grant", "академ", "academic",
                    "молодеж", "youth", "подросток", "teenager", "рождаемость", "birth rate", "смертность", "mortality",
                    "брак", "marriage", "развод", "divorce", "усыновлен", "adoption", "опек", "guardianship",
                    "инвалид", "disabled", "пенсионер", "pensioner", "ветеран", "veteran", "репатриант", "repatriate",
                    "мигрант", "migrant", "эмигрант", "emigrant", "иммигрант", "immigrant", "беженец", "refugee",
                    "толерант", "tolerance", "дискриминац", "discrimination", "равенство", "equality", "справедливость", "justice",
                    "волонтер", "volunteer", "благотворительн", "charity", "фонд", "foundation", "помощ", "aid", "help",
                    "традиц", "tradition", "обыча", "custom", "религи", "religion", "вер", "faith", "конфесс", "denomination",
                    "этнич", "ethnic", "национ", "national", "менталитет", "mentality", "ценност", "value", "норм", "norm",
                    "этик", "ethics", "морал", "morality", "нравствен", "morals", "совесть", "conscience", "порядочн", "decency",
                    "гражданск", "civic", "патриот", "patriot", "лобб", "lobby", "актив", "activism", "протест", "protest",
                    "демонстрац", "demonstration", "митинг", "rally", "пикет", "picket", "забастовк", "strike",
                    "инициатив", "initiative", "петиц", "petition", "референдум", "referendum", "опрос", "poll", "опросник", "questionnaire")),

    ACCIDENTS("Accidents",
            Arrays.asList("происшестви", "accidents", "accident", "emergency",
                    "авари", "crash", "преступлени", "crime", "criminal",
                    "пожар", "fire", "дтп", "traffic accident", "катастроф",
                    "disaster", "catastrophe", "emergency", "rescue",
                    "взрыв", "explosion", "blast", "стрельб", "shooting", "насил", "violence", "убийств", "murder",
                    "ограблен", "robbery", "краж", "theft", "вымогательств", "extortion", "вымога", "extort",
                    "вымогал", "blackmail", "шантаж", "blackmail", "похищен", "abduction", "kidnapping", "захват", "capture",
                    "заложник", "hostage", "теракт", "terrorist act", "терроризм", "terrorism", "экстремизм", "extremism",
                    "разборк", "showdown", "драк", "fight", "побо", "battery", "избиен", "beating", "истязан", "torture",
                    "суицид", "suicide", "самоубийств", "suicide", "отравлен", "poisoning", "интоксикац", "intoxication",
                    "травм", "injury", "ранения", "wound", "увеч", "mutilation", "инвалидност", "disability",
                    "пропаж", "disappearance", "исчезновен", "disappearance", "поиск", "search", "спасен", "rescue",
                    "ликвидац", "liquidation", "обвал", "collapse", "обрушен", "collapse", "затоплен", "flooding",
                    "оползн", "landslide", "сель", "mudflow", "лавин", "avalanche", "цунам", "tsunami",
                    "эпидем", "epidemic", "пандем", "pandemic", "карантин", "quarantine", "изоляц", "isolation",
                    "санитарн", "sanitary", "эпидемиолог", "epidemiologist", "вирусолог", "virologist", "инфекц", "infection")),

    SPORT("Sport",
            Arrays.asList("футбол", "football",
                    "хоккей", "hockey", "олимпиад", "олимпийск", "olympics", "соревновани",
                    "competition", "чемпионат", "championship", "матч", "match",
                    "game", "basketball", "tennis", "athletics", "athlete",
                    "спорт", "sport", "спортсмен", "sportsman", "athlete", "тренер", "coach", "команд", "team",
                    "лига", "league", "кубок", "cup", "турнир", "tournament", "плей-офф", "playoff", "финал", "final",
                    "полуфинал", "semifinal", "четвертьфинал", "quarterfinal", "групповой этап", "group stage",
                    "болельщик", "fan", "фанат", "fan", "стадион", "stadium", "арен", "arena", "спорткомплекс", "sports complex",
                    "тренировк", "training", "разминк", "warm-up", "зарядк", "exercise", "физкультур", "physical education",
                    "гимнастик", "gymnastics", "плаван", "swimming", "бег", "running", "ходьб", "walking", "прыжк", "jump",
                    "метан", "throwing", "тяжел", "weight", "легк", "light", "единоборств", "martial arts",
                    "бокс", "boxing", "борьб", "wrestling", "дзюдо", "judo", "каратэ", "karate", "тайск", "thai",
                    "фехтован", "fencing", "стрельб", "shooting", "конный спорт", "equestrian", "велоспорт", "cycling",
                    "автоспорт", "motorsport", "мотоспорт", "motorsport", "парусный спорт", "sailing", "гребл", "rowing",
                    "лыж", "skiing", "сноуборд", "snowboarding", "коньк", "skating", "биатлон", "biathlon", "триатлон", "triathlon",
                    "пляж", "beach", "экстремальн", "extreme", "паралимпийск", "paralympic", "сурдлимпийск", "deaflympic",
                    "спортивн", "sports", "физподготовк", "physical training", "реабилитац", "rehabilitation", "восстановлен", "recovery")),

    SCIENCE_TECH("Science and tech",
            Arrays.asList("наук", "научн", "science", "scientific", "технологи", "technology",
                    "tech", "it", "information technology", "космос", "space",
                    "инноваци", "innovation", "исследовани", "research", "discovery",
                    "artificial intelligence", "ai", "machine learning", "robotics",
                    "computer", "software", "hardware", "internet", "digital", "учены", "учёны", "scientist",
                    "ии", "ai", "робот", "robot", "кибер", "cyber", "данн", "data", "big data", "большие данные",
                    "алгоритм", "algorithm", "программ", "software", "приложен", "application", "app", "дев", "dev", "developer",
                    "код", "code", "python", "sql", "язык программирования", "programming language", "интернет", "internet",
                    "цифров", "digital", "гаджет", "gadget", "смартфон", "smartphone", "ноутбук", "laptop", "планшет", "tablet",
                    "телеком", "telecom", "оператор", "operator", "связ", "connection", "мобильн", "mobile", "wi-fi", "wi-fi",
                    "bluetooth", "bluetooth", "5g", "5g", "6g", "6g", "спутник", "satellite", "навигац", "navigation", "gps", "gps",
                    "кибербезопасность", "cybersecurity", "хакер", "hacker", "взлом", "hack", "вирус", "virus", "malware", "malware",
                    "шифрован", "encryption", "криптограф", "cryptography", "блокчейн", "blockchain", "криптовалют", "cryptocurrency",
                    "майнинг", "mining", "нейросет", "neural network", "глубинное обучение", "deep learning", "обработк", "processing",
                    "компьютерное зрение", "computer vision", "распознаван", "recognition", "синтез", "synthesis", "генерац", "generation",
                    "квантов", "quantum", "биотех", "biotech", "геном", "genome", "редактирован", "editing", "генная инженерия", "genetic engineering",
                    "наноматериал", "nanomaterial", "нанотехнолог", "nanotechnology", "композит", "composite", "графен", "graphene",
                    "аддитивн", "additive", "3d-печать", "3d printing", "прототип", "prototype", "лаборатор", "laboratory", "эксперимент", "experiment",
                    "открыт", "discovery", "патент", "patent", "изобретен", "invention", "интеллектуальн", "intellectual", "ip", "ip")),

    CULTURE("Culture",
            Arrays.asList("культур", "culture", "cultural", "искусств", "art",
                    "кино", "cinema", "movie", "фильм", "film", "музык", "music",
                    "театр", "theater", "theatre", "литератур", "literature",
                    "выставк", "exhibition", "музе", "museum", "heritage",
                    "арт", "art", "худож", "artist", "скульптур", "sculpture", "живопис", "painting", "рисун", "drawing",
                    "граффити", "graffiti", "перформанс", "performance", "инсталляц", "installation", "коллекц", "collection",
                    "галере", "gallery", "вернисаж", "vernissage", "презентац", "presentation", "шоу", "show", "концерт", "concert",
                    "фестивал", "festival", "карнавал", "carnival", "праздник", "holiday", "торжеств", "celebration", "юбилей", "anniversary",
                    "памятник", "monument", "мемориал", "memorial", "достопримечательн", "attraction", "историческ", "historical",
                    "археолог", "archaeology", "артефакт", "artifact", "реликви", "relic", "наслед", "heritage", "традиц", "tradition",
                    "фольклор", "folklore", "эпос", "epic", "легенд", "legend", "миф", "myth", "сказк", "fairy tale", "былин", "epic",
                    "поэз", "poetry", "проз", "prose", "роман", "novel", "повест", "story", "рассказ", "short story", "эссе", "essay",
                    "драматург", "playwright", "сценарист", "screenwriter", "режиссер", "director", "актер", "actor", "актрис", "actress",
                    "опер", "opera", "балет", "ballet", "симфония", "symphony", "оркестр", "orchestra", "хор", "choir", "солист", "soloist",
                    "дирижер", "conductor", "композитор", "composer", "аранжиров", "arrangement", "звукозап", "recording", "студи", "studio",
                    "кинематограф", "cinematography", "анимац", "animation", "мультфильм", "cartoon", "документал", "documentary", "короткометраж", "short film",
                    "сериал", "series", "телевид", "television", "радио", "radio", "подкаст", "podcast", "блог", "blog", "влог", "vlog",
                    "мем", "meme", "стрим", "stream", "трансляц", "broadcast", "веща", "broadcasting")),

    HEALTH("Health",
            Arrays.asList("здоров", "health", "healthcare", "медицин", "medicine",
                    "medical", "болезн", "disease", "illness", "лечени", "treatment",
                    "врачи", "doctors", "physician", "больниц", "hospital",
                    "клиник", "clinic", "вирус", "virus", "pandemic", "epidemic",
                    "vaccine", "vaccination", "pharmacy", "drug",
                    "здравоохран", "healthcare", "медик", "medic", "хирург", "surgeon", "терапевт", "therapist", "педиатр", "pediatrician",
                    "стоматолог", "dentist", "офтальмолог", "ophthalmologist", "кардиолог", "cardiologist", "онколог", "oncologist",
                    "невролог", "neurologist", "психиатр", "psychiatrist", "психолог", "psychologist", "психотерапевт", "psychotherapist",
                    "диетолог", "nutritionist", "физиотерапевт", "physiotherapist", "реабилитолог", "rehabilitation specialist", "логопед", "speech therapist",
                    "пациент", "patient", "больн", "sick", "заболеван", "disease", "инфекц", "infection", "воспален", "inflammation",
                    "травм", "injury", "перелом", "fracture", "ушиб", "bruise", "растяжен", "sprain", "вывих", "dislocation",
                    "операц", "surgery", "хирургическ", "surgical", "анестез", "anesthesia", "наркоз", "anesthesia", "реанимац", "resuscitation",
                    "интенсив", "intensive", "скор", "ambulance", "неотложк", "emergency", "рецепт", "prescription", "лекарств", "medicine", "drug",
                    "препарат", "drug", "медикамент", "medication", "таблетк", "tablet", "капсул", "capsule", "сироп", "syrup", "маз", "ointment",
                    "инъекц", "injection", "укол", "injection", "капельниц", "drip", "переливан", "transfusion", "донорств", "donation",
                    "профилактик", "prevention", "диагност", "diagnosis", "обследован", "examination", "анализ", "test", "лаборатор", "laboratory",
                    "рентген", "x-ray", "узи", "ultrasound", "кт", "ct", "мрт", "mri", "эндоскоп", "endoscopy", "биопс", "biopsy",
                    "генетическ", "genetic", "наследствен", "hereditary", "хроническ", "chronic", "остр", "acute", "ремисс", "remission",
                    "инвалидност", "disability", "протез", "prosthesis", "ортез", "orthosis", "костыл", "crutch", "инвалидн", "wheelchair",
                    "психическ", "mental", "депресс", "depression", "тревож", "anxiety", "стресс", "stress", "психоз", "psychosis", "невроз", "neurosis",
                    "зависимост", "addiction", "алкогол", "alcohol", "наркотик", "drug", "табак", "tobacco", "курение", "smoking",
                    "здоровый образ жизни", "healthy lifestyle", "диет", "diet", "питание", "nutrition", "витамин", "vitamin", "минерал", "mineral",
                    "фитнес", "fitness", "йог", "yoga", "медитац", "meditation", "релаксац", "relaxation", "детокс", "detox", "очищен", "cleansing")),

    TRAVELS("Travels and tourism",
            Arrays.asList("туризм", "турист", "tourism", "travel", "travel", "путешестви",
                    "journey", "отдых", "vacation", "holiday", "курорт", "resort",
                    "отели", "hotels", "hotel", "отпуск", "leave", "экскурси",
                    "excursion", "tour", "trip", "destination", "airline",
                    "авиаперевоз", "air transportation", "авиакомпания", "airline", "аэропорт", "airport", "рейс", "flight",
                    "билет", "ticket", "бронь", "booking", "резервирован", "reservation", "поездк", "trip", "вояж", "voyage",
                    "пассажир", "passenger", "багаж", "luggage", "чемодан", "suitcase", "рюкзак", "backpack", "дорожн", "travel",
                    "гид", "guide", "туроператор", "tour operator", "турагент", "travel agent", "путевк", "voucher", "пут", "trip",
                    "маршрут", "route", "итенирар", "itinerary", "карт", "map", "навигац", "navigation", "ориентирован", "orientation",
                    "достопримечательн", "attraction", "памятник", "monument", "музе", "museum", "галере", "gallery", "заповедник", "reserve",
                    "национальн", "national", "парк", "park", "заказник", "sanctuary", "ландшафт", "landscape", "пейзаж", "landscape",
                    "пляж", "beach", "море", "sea", "океан", "ocean", "гор", "mountain", "лес", "forest", "река", "river", "озер", "lake",
                    "кемпинг", "camping", "палатка", "tent", "коттедж", "cottage", "вилл", "villa", "шале", "chalet", "хостел", "hostel",
                    "мотель", "motel", "гостиниц", "hotel", "постоял", "inn", "апартамент", "apartment", "кондоминиум", "condominium",
                    "аренд", "rental", "прокат", "rental", "квартир", "apartment", "дом отдыха", "resort", "санатор", "sanatorium",
                    "спа", "spa", "оздоровительн", "health", "рекреац", "recreation", "развлечен", "entertainment", "аттракцион", "attraction",
                    "аквапарк", "water park", "дельфинарий", "dolphinarium", "зоопарк", "zoo", "ботаническ", "botanical", "сафари", "safari",
                    "экстремальн", "extreme", "экзотическ", "exotic", "колорит", "local color", "традиц", "tradition", "кухня", "cuisine",
                    "сувенир", "souvenir", "покупк", "shopping", "дьюти-фри", "duty-free", "таможн", "customs", "виз", "visa", "паспорт", "passport",
                    "страховк", "insurance", "медицинск", "medical", "трансфер", "transfer", "шаттл", "shuttle", "аренд", "rental", "прокат", "rental")),

    WEATHER("Weather",
            Arrays.asList("погод", "weather", "климат", "climate", "природ",
                    "nature", "экологи", "ecology", "environment", "катаклизм",
                    "cataclysm", "наводнени", "flood", "flooding", "землетрясени",
                    "earthquake", "storm", "hurricane", "typhoon", "temperature",
                    "forecast", "global warming", "climate change",
                    "метеоролог", "meteorology", "метео", "meteo", "синоптик", "weather forecaster", "прогноз", "forecast",
                    "осадк", "precipitation", "дожд", "rain", "снег", "snow", "град", "hail", "иней", "frost", "измороз", "hoarfrost",
                    "туман", "fog", "мгл", "haze", "облачн", "cloud", "ясн", "clear", "солнечн", "sunny", "пасмурн", "cloudy",
                    "ветер", "wind", "ураган", "hurricane", "торнад", "tornado", "смерч", "tornado", "шторм", "storm", "гроз", "thunderstorm",
                    "молн", "lightning", "гром", "thunder", "радуг", "rainbow", "полярн", "polar", "сияние", "aurora",
                    "температур", "temperature", "жара", "heat", "зно", "heat", "холод", "cold", "мороз", "frost", "лед", "ice",
                    "гололед", "black ice", "гололедиц", "ice", "снегопад", "snowfall", "метел", "blizzard", "пург", "blizzard", "вьюг", "blizzard",
                    "зано", "snowdrift", "снежн", "snowy", "лавин", "avalanche", "сель", "mudflow", "оползн", "landslide", "обвал", "collapse",
                    "цунам", "tsunami", "прилив", "tide", "отлив", "ebb", "волн", "wave", "штормов", "storm", "затоплен", "flooding",
                    "подтоплен", "flooding", "паводок", "flood", "половод", "high water", "засух", "drought", "засушл", "arid", "сухове", "dry wind",
                    "пыльн", "dusty", "песчан", "sand", "буря", "storm", "смог", "smog", "загрязнен", "pollution", "экологическ", "ecological",
                    "катаклизм", "cataclysm", "стихийн", "natural", "бедств", "disaster", "катастроф", "disaster", "чрезвычайн", "emergency",
                    "потеплен", "warming", "похолодан", "cooling", "аномал", "anomaly", "рекорд", "record", "минимум", "minimum", "максимум", "maximum",
                    "средне", "average", "норм", "norm", "отклонен", "deviation", "циклон", "cyclone", "антициклон", "anticyclone", "фронт", "front",
                    "воздушн", "air", "масс", "mass", "атмосфер", "atmospheric", "давлен", "pressure", "влажност", "humidity", "точк", "dew point")),

    UNKNOWN("Unknown", List.of());

    private final String displayName;
    private final List<String> keywords;

    Topic(String displayName, List<String> keywords) {
        this.displayName = displayName;
        this.keywords = keywords;
    }

    /**
     * Get display name for the topic
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Try to determine topic from string
     */
    public static Topic determineFromString(String string) {
        if (string == null || string.isEmpty()) {
            return UNKNOWN;
        }

        String lowerString = string.toLowerCase();

        // Check each topic's keywords
        for (Topic topic : values()) {
            if (topic == UNKNOWN) continue;

            for (String keyword : topic.keywords) {
                if (lowerString.contains(keyword)) {
                    return topic;
                }
            }
        }
        return UNKNOWN;
    }
}