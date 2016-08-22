package org.ow2.proactive.addons.webhook;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ow2.proactive.addons.webhook.exception.UnsuccessfulRequestException;
import org.ow2.proactive.addons.webhook.service.JsonRestRequestService;

import java.lang.reflect.Field;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WebhookTest {

    @Mock
    static WebhookExecutor webhookExecutor;

    Webhook webhook = new Webhook();

    @Before
    public void setUp() throws UnsuccessfulRequestException, NoSuchFieldException, IllegalAccessException {
        doNothing().when(webhookExecutor).execute(any(),any(),any(),any());
        overwriteStaticPrivateFieldForTest();
    }

    @Test
    public void testThatWebhookCallsWebhookExecutor() throws Throwable {
        webhook.execute("test","test","test","test");
        verify(webhookExecutor).execute(any(),any(),any(),any());
    }

    private void overwriteStaticPrivateFieldForTest() throws NoSuchFieldException, IllegalAccessException {
        Field staticWebhookInstance = Webhook.class.getDeclaredField("webhookExecutor");
        staticWebhookInstance.setAccessible(true);
        staticWebhookInstance.set(webhook,webhookExecutor);
        staticWebhookInstance.setAccessible(false);
    }
}