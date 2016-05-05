package module.custom.jugnig.mobilox.data;

/**
 * Created by JugniG on 05-05-2016.
 */
public class FeedBackModel {

    private String req;
    private String full_name;
    private String email;
    private String contact;
    private String country;
    private String os;
    private String feedbacktype;
    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    public void setReq(String req) {
        this.req = req;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public void setFeedbacktype(String feedbacktype) {
        this.feedbacktype = feedbacktype;
    }
}
