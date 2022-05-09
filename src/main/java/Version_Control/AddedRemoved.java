package Version_Control;

public class AddedRemoved {
    int addedLineNumber;
    String addedLineContent;
    int removedLineNumber;
    String removedLineContent;
    public AddedRemoved(int addedLineNumber, String addedLineContent, int removedLineNumber, String removedLineContent){
        this.addedLineNumber = addedLineNumber;
        this.addedLineContent = addedLineContent;
        this.removedLineNumber = removedLineNumber;
        this.removedLineContent = removedLineContent;
    }

    public void setAddedLineContent(String addedLineContent) {
        this.addedLineContent = addedLineContent;
    }

    public void setAddedLineNumber(int addedLineNumber) {
        this.addedLineNumber = addedLineNumber;
    }

    public void setRemovedLineContent(String removedLineContent) {
        this.removedLineContent = removedLineContent;
    }

    public void setRemovedLineNumber(int removedLineNumber) {
        this.removedLineNumber = removedLineNumber;
    }

    public int getAddedLineNumber() {
        return addedLineNumber;
    }

    public int getRemovedLineNumber() {
        return removedLineNumber;
    }

    public String getAddedLineContent() {
        return addedLineContent;
    }

    public String getRemovedLineContent() {
        return removedLineContent;
    }
}
