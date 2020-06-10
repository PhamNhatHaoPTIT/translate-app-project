package com.example.apaar97.translate;

public class Word {
    private int id;
    private String codeFrom, codeTo, wordFrom, wordTo;
    private int isFavorite, isDelete;

    public Word() {

    }

    public Word(String codeFrom, String codeTo, String wordFrom, String wordTo) {
        this.codeFrom = codeFrom;
        this.codeTo = codeTo;
        this.wordFrom = wordFrom;
        this.wordTo = wordTo;
        isFavorite = 0;
        isDelete = 0;
    }

    public String getCodeFrom() {
        return codeFrom;
    }

    public void setCodeFrom(String codeFrom) {
        this.codeFrom = codeFrom;
    }

    public String getCodeTo() {
        return codeTo;
    }

    public void setCodeTo(String codeTo) {
        this.codeTo = codeTo;
    }

    public String getWordFrom() {
        return wordFrom;
    }

    public void setWordFrom(String wordFrom) {
        this.wordFrom = wordFrom;
    }

    public String getWordTo() {
        return wordTo;
    }

    public void setWordTo(String wordTo) {
        this.wordTo = wordTo;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Word{" +
                "codeFrom='" + codeFrom + '\'' +
                ", codeTo='" + codeTo + '\'' +
                ", wordFrom='" + wordFrom + '\'' +
                ", wordTo='" + wordTo + '\'' +
                '}';
    }
}
