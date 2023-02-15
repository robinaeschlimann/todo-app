package ch.hftm.todo.service;

import ch.hftm.todo.caches.PersonCache;
import ch.hftm.todo.model.EPermission;
import ch.hftm.todo.model.PersonData;
import ch.hftm.todo.service.exception.DeleteException;
import ch.hftm.todo.stores.PersonStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith( MockitoExtension.class )
class PersonServiceTest
{
    private PersonService service;
    @Mock
    private PersonCache personCache;

    @Mock
    private PersonStore personStore;

    @BeforeEach
    void setUp() {
        service = PersonService.getInstance();
    }

    @Test
    void getAll() {
        try ( MockedStatic<PersonCache> cache = Mockito.mockStatic(PersonCache.class)) {
            List<PersonData> personData = generatePersonData();

            when( personCache.getPersons() ).thenReturn( personData );

            cache.when( PersonCache::getInstance ).thenReturn( personCache );

            var persons = service.getAll();

            // verify the getPerson method of the personCache is called once
            verify( personCache, times( 1 ) ).getPersons();

            assertThat( persons, hasItems( personData.toArray( new PersonData[0] ) ) );
        }
    }

    @Test
    void get() {
        try ( MockedStatic<PersonCache> cache = Mockito.mockStatic(PersonCache.class)) {
            PersonData personData = PersonData.builder().build();

            when( personCache.getPerson( 1 ) ).thenReturn( personData );

            cache.when( PersonCache::getInstance ).thenReturn( personCache );

            var persons = service.get( 1 );

            // verify the getPerson method of the personCache is called once
            verify( personCache, times( 1 ) ).getPerson( 1 );

            assertThat( persons, is( personData ) );
        }
    }

    @Test
    void save() {
        try ( MockedStatic<PersonStore> store = Mockito.mockStatic(PersonStore.class)) {

            PersonData personData = PersonData.builder().build();

            store.when( PersonStore::getInstance ).thenReturn( personStore );

           service.save( personData );

            verify( personStore, times( 1 ) ).savePerson( personData );
        }
    }

    @Test
    void delete() throws DeleteException {
        try ( MockedStatic<PersonCache> cache = Mockito.mockStatic(PersonCache.class);
            MockedStatic<PersonStore> store = Mockito.mockStatic( PersonStore.class ) ) {
            cache.when( PersonCache::getInstance ).thenReturn( personCache );
            store.when( PersonStore::getInstance ).thenReturn( personStore );

            List<PersonData> personData = generatePersonData();
            personData.add( PersonData.builder()
                    .permission(EPermission.ADMIN.getId())
                    .build() );

            when( personCache.getPersons() ).thenReturn( personData );
            when( personStore.deletePerson( 1 ) ).thenReturn( true );

            service.delete( 1 );

            verify( personStore, times( 1 ) ).deletePerson( 1 );
        }
    }

    @Test
    void deleteLastAdmin()
    {
        try ( MockedStatic<PersonCache> cache = Mockito.mockStatic(PersonCache.class)) {
            cache.when( PersonCache::getInstance ).thenReturn( personCache );

            List<PersonData> personData = generatePersonData();

            when( personCache.getPersons() ).thenReturn( personData );

            assertThrows( DeleteException.class, () -> service.delete( 1 ) );
        }
    }

    @Test
    void deleteFailed() {
        try ( MockedStatic<PersonCache> cache = Mockito.mockStatic(PersonCache.class);
              MockedStatic<PersonStore> store = Mockito.mockStatic( PersonStore.class ) ) {
            cache.when( PersonCache::getInstance ).thenReturn( personCache );
            store.when( PersonStore::getInstance ).thenReturn( personStore );

            List<PersonData> personData = generatePersonData();
            personData.add( PersonData.builder()
                    .permission(EPermission.ADMIN.getId())
                    .build() );

            when( personCache.getPersons() ).thenReturn( personData );
            when( personStore.deletePerson( 1 ) ).thenReturn( false );

            assertThrows( DeleteException.class, () -> service.delete( 1 ) );

            verify( personStore, times( 1 ) ).deletePerson( 1 );
        }
    }

    @Test
    void getNextFreeId() {
        try ( MockedStatic<PersonCache> cache = Mockito.mockStatic(PersonCache.class) )
        {
            cache.when( PersonCache::getInstance ).thenReturn( personCache );
            when( personCache.getNextFreeId() ).thenReturn( 3 );

            var result = service.getNextFreeId();

            verify( personCache, times( 1 ) ).getNextFreeId();

            assertThat( result, is( 3 ) );
        }
    }

    private List<PersonData> generatePersonData()
    {
        PersonData personData = PersonData.builder().build();
        PersonData personData2 = PersonData.builder().build();

        return new ArrayList<>( List.of( personData, personData2 ) );
    }
}