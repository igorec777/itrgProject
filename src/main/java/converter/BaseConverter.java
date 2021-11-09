package converter;


public interface BaseConverter<A, B> {

    default B fromDto(A dto) {
        return null;
    }

    default A toDto(B entity) {
        return null;
    }
}
