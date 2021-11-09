package converter;


public interface BaseConverter<A, B>  {

     B fromDto(A dto);

    A toDto(B entity);
}
