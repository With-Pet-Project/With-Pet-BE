package WebProject.withpet.articles.domain;

public enum Tag {
    LOST, WALK, GOODS, PLACE, HOSPITAL, ETC;

    public static Boolean isSpecTag(Tag tag){
        if (tag.equals(Tag.LOST) || tag.equals(Tag.WALK) || tag.equals(Tag.HOSPITAL)) {
            return true;
        } else {
            return false;
        }
    }
}
