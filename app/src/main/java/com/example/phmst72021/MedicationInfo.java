package com.example.phmst72021;

public class MedicationInfo {
    private String medicationName;
    private String doctorsName;
    private String dateMedGiven;
    private String dateMedExp;
    private String medAmount;
    private String dosageAmount;
    private String generalNotes;

    public MedicationInfo(){

    }

    // getter and setter for med name
    public String getMedicationName(){
        return medicationName;
    }


    public void setMedicationName(){
        this.medicationName = medicationName;
    }

    // getter and setter for doc name
    public String getDoctorsName(){
        return doctorsName;
    }
    public void setDoctorsName(){
        this.doctorsName = doctorsName;
    }

    //getter and setter for date med given
    public String getDateMedGiven(){
        return dateMedGiven;
    }
    public void setDateMedGiven(){
        this.dateMedGiven = dateMedGiven;
    }

    //getter and setter for date med exp
    public String getDateMedExp(){
        return dateMedExp;
    }
    public void setDateMedExp(){
        this.dateMedExp = dateMedExp;
    }

    // getter and setter for med amount
    public String getMedAmount(){
        return getMedAmount();
    }
    public void setMedAmount(){
        this.medAmount = medAmount;
    }

    //getter and setter for dosage amount
    public String getDosageAmount(){
        return dosageAmount;
    }
    public void setDosageAmount(){
        this.dosageAmount = dosageAmount;
    }

    //getter and setter for general notes
    public String getGeneralNotes(){
        return generalNotes;
    }
    public void setGeneralNotes(){
        this.generalNotes = generalNotes;
    }
}
