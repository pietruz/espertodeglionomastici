package it.trmn.espertodeglionomastici.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Onomastico {

@SerializedName("santo")
@Expose
private String santo;
@SerializedName("altri")
@Expose
private List<String> altri = null;

public String getSanto() {
return santo;
}

public void setSanto(String santo) {
this.santo = santo;
}

public List<String> getAltri() {
return altri;
}

public void setAltri(List<String> altri) {
this.altri = altri;
}

}