package org.tmt.sample.core;

import esw.http.template.wiring.JCswServices;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.mockito.Mockito;
import org.scalatestplus.junit.JUnitSuite;
import org.tmt.sample.core.models.SampleResponse;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;

public class JSampleImplTest extends JUnitSuite {

  @Test
  public void shouldCallBye() throws ExecutionException, InterruptedException {
    JCswServices mock = Mockito.mock(JCswServices.class);
    JSampleImpl jSample = new JSampleImpl(mock);
    SampleResponse sampleResponse = new SampleResponse("Bye!!!");
    assertThat(jSample.sayBye().get(), CoreMatchers.is(sampleResponse));
  }
}
