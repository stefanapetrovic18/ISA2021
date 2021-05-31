package rs.apoteka.dto;

public class ComplaintResponse {
    private Long complaintID;
    private String text;

    public ComplaintResponse() {
    }

    public Long getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(Long complaintID) {
        this.complaintID = complaintID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
