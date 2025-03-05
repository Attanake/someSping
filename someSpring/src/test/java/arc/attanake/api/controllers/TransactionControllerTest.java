package arc.attanake.api.controllers;

import arch.attanake.api.controllers.TransactionController;
import arch.attanake.api.exceptions.BadRequestException;
import arch.attanake.store.entities.CardAccountEntity;
import arch.attanake.store.entities.ClientEntity;
import arch.attanake.store.repositories.CardAccountRepository;
import arch.attanake.store.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TransactionControllerTest {

    @Mock
    private CardAccountRepository cardAccountRepository;

    @Mock
    private CardAccountEntity cardAccount;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionController transactionController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    void transactionClientCannotBeFounded(){
        Long firstClientId = 1L;
        Long secondClientId = 2L;
        when(cardAccountRepository.findByAccId(firstClientId)).thenReturn(null);
        when(cardAccountRepository.findByAccId(secondClientId)).thenReturn(null);
        Assertions.assertThrows(BadRequestException.class,() -> transactionController.createTransaction(firstClientId, secondClientId, BigDecimal.valueOf(1)));
    }

    @Test
    void transactionZeroValue() throws Exception {
        Long firstCardAccountId = 1L;
        Long secondCardAccountId = 2L;
        Long clientId = 1L;

        ClientEntity client = new ClientEntity();
        client.setClientId(clientId);


        CardAccountEntity firstCardAccount = new CardAccountEntity(firstCardAccountId,
                null,
                BigDecimal.valueOf(1000L),
                null,
                1,
                client);

        CardAccountEntity secondCardAccount = new CardAccountEntity(secondCardAccountId,
                null,
                BigDecimal.valueOf(1000L),
                null,
                1,
                client);
        
        when(cardAccountRepository.findByAccId(firstCardAccountId)).thenReturn(Optional.of(firstCardAccount));
        when(cardAccountRepository.findByAccId(secondCardAccountId)).thenReturn(Optional.of(secondCardAccount));

        mockMvc.perform(post("/api/transactions")
                .content("1")
                .content("2")
                .content("0"))
                .andExpect(status().isBadRequest());
    }
}
