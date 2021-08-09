package com.example.phmst72021;

public class MedicationInfo {

    String MedName;
    String DocName;
    String AmountGiven;
    String GivenDate;
    String ExpDate;
    String DosageAmount;
    String Notes;

    public MedicationInfo() {
    }

    public MedicationInfo(String medName, String docName, String amountGiven, String givenDate, String expDate, String dosageAmount, String notes) {
        MedName = medName;
        DocName = docName;
        AmountGiven = amountGiven;
        GivenDate = givenDate;
        ExpDate = expDate;
        DosageAmount = dosageAmount;
        Notes = notes;
    }

    public String getMedName() {
        return MedName;
    }

    public String getDocName() {
        return DocName;
    }

    public String getAmountGiven() {
        return AmountGiven;
    }

    public String getGivenDate() {
        return GivenDate;
    }

    public String getExpDate() {
        return ExpDate;
    }

    public String getDosageAmount() {
        return DosageAmount;
    }

    public String getNotes() {
        return Notes;
    }

    public void setMedName(String medName) {
        MedName = medName;
    }

    public void setDocName(String docName) {
        DocName = docName;
    }

    public void setAmountGiven(String amountGiven) {
        AmountGiven = amountGiven;
    }

    public void setGivenDate(String givenDate) {
        GivenDate = givenDate;
    }

    public void setExpDate(String expDate) {
        ExpDate = expDate;
    }

    public void setDosageAmount(String dosageAmount) {
        DosageAmount = dosageAmount;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }
}