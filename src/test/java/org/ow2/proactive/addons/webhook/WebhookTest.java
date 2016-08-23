package org.ow2.proactive.addons.webhook;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ow2.proactive.addons.webhook.exception.UnsuccessfulRequestException;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WebhookTest {

    @SuppressWarnings("CanBeFinal")
    @Mock
    static WebhookExecutor webhookExecutor;

    @SuppressWarnings("CanBeFinal")
    private Webhook webhook = new Webhook();

    @Before
    public void setUp() throws UnsuccessfulRequestException, NoSuchFieldException, IllegalAccessException, IOException {
        doNothing().when(webhookExecutor).execute(any(), any(), any(), any());
        overwriteStaticPrivateFieldForTest();
    }

    @Test
    public void testThatWebhookCallsWebhookExecutor() throws Throwable {
        Webhook.execute("test", "test", "test", "test");
        verify(webhookExecutor).execute(any(), any(), any(), any());
    }

    private void overwriteStaticPrivateFieldForTest() throws NoSuchFieldException, IllegalAccessException {
        Field staticWebhookInstance = Webhook.class.getDeclaredField("webhookExecutor");
        staticWebhookInstance.setAccessible(true);
        staticWebhookInstance.set(webhook, webhookExecutor);
        staticWebhookInstance.setAccessible(false);
    }
}