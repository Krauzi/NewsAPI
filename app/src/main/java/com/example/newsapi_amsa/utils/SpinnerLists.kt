package com.example.newsapi_amsa.utils

data class Language(val key: String, val value: String) {
    override fun toString(): String {
        return key
    }
}

data class QueryType(val key: String, val value: String) {
    override fun toString(): String {
        return key
    }
}

data class OlderThan(val key: String, val value: String) {
    override fun toString(): String {
        return key
    }
}

data class SortBy(val key: String, val value: String) {
    override fun toString(): String {
        return key
    }
}

class SpinnerLists {
    companion object {
        var language = mutableListOf(
            Language("Angielski", "en"),
            Language("Francuski", "fr"),
            Language("Hiszpański", "es"),
            Language("Portugalski", "pt"),
            Language("Rosyjski", "ru"),
            Language("Niemiecki", "de")
        )

        var queryType = mutableListOf(
            QueryType("Tytuł i zawartość artykułu", "q"),
            QueryType("Tylko w tytule", "qInTitle")
        )

        var olderThan = mutableListOf(
            OlderThan("Trzy godziny", Utils.getHoursAgo(3)),
            OlderThan("Sześć godzin", Utils.getHoursAgo(6)),
            OlderThan("Dwanaście godzin", Utils.getHoursAgo(12)),
            OlderThan("Dzień", Utils.getDaysAgo(1)),
            OlderThan("Trzy dni", Utils.getDaysAgo(3)),
            OlderThan("Tydzień", Utils.getDaysAgo(7))
        )

        var sortBy = mutableListOf(
            SortBy("Publikacja", "publishedAt"),
            SortBy("Rzetelność", "relevancy"),
            SortBy("Popularność", "popularity")
        )
    }
}