package arc.attanake.api.controllers;

import arch.attanake.api.controllers.TransactionController;
import arch.attanake.api.dto.TransactionDto;
import arch.attanake.api.exceptions.BadRequestException;
import arch.attanake.api.factroies.CardAccountDtoFactory;
import arch.attanake.api.factroies.CardAccountTypeDtoFactory;
import arch.attanake.api.factroies.TransactionDtoFactory;
import arch.attanake.store.entities.CardAccountEntity;
import arch.attanake.store.entities.ClientEntity;
import arch.attanake.store.repositories.CardAccountRepository;
import arch.attanake.store.repositories.TransactionRepository;
import net.bytebuddy.dynamic.DynamicType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.smartcardio.Card;
import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;

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


    @Test
    void transactionClientCannotBeFounded(){
        Long firstClientId = 1L;
        Long secondClientId = 2L;
        when(cardAccountRepository.findByAccId(firstClientId)).thenReturn(null);
        when(cardAccountRepository.findByAccId(secondClientId)).thenReturn(null);
        Assertions.assertThrows(BadRequestException.class,() -> transactionController.createTransaction(firstClientId, secondClientId, BigDecimal.valueOf(1)));
    }

    @Test
    void transactionZeroValue(){
        Long cardAccountId = 1L;
        Long clientId = 1L;

        TransactionController controller = new TransactionController(cardAccountRepository,transactionRepository);

        ClientEntity client = new ClientEntity();
        client.setClientId(clientId);


        cardAccount = cardAccountRepository.saveAndFlush(
                CardAccountEntity.builder()
                        .accId(cardAccountId)
                        .amountOnAcc(BigDecimal.valueOf(1000L))
                        .build()
        );
        
        when(cardAccountRepository.findByAccId(cardAccountId)).thenReturn(Optional.of(cardAccount));

        Assertions.assertEquals(
                Boolean.valueOf("True"),
                controller.createTransaction(cardAccountId, cardAccountId, BigDecimal.valueOf(100L)));

    }
}
