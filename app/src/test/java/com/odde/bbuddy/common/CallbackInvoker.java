package com.odde.bbuddy.common;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.Stubber;

import static org.mockito.Mockito.doAnswer;

public class CallbackInvoker {

    public static Stubber callConsumerArgumentAtIndexWith(final int index, final Object data) {
        return doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Consumer consumer = invocation.getArgument(index);
                consumer.accept(data);
                return null;
            }
        });
    }

    public static Stubber callRunnableAtIndex(final int index) {
        return doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Runnable afterSuccess = invocation.getArgument(index);
                afterSuccess.run();
                return null;
            }
        });
    }

}
