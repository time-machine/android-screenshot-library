package com.github.rtyley.android.screenshot.paparazzo;

//
import static java.util.Arrays.asList;

import com.android.ddmlib.IDevice;
import com.android.ddmlib.MultiLineReceiver;
//
import com.github.rtyley.android.screenshot.paparazzo.processors.ScreenshotProcessor;
//
import java.util.List;
//

public class OnDemandScreenshotService {
  private final IDevice device;
  private final List<ScreenshotProcessor> processors;
  private final ScreenshotRequestListener logListenerCommandShell;

  public OnDemandScreenshotService(IDevice device, ScreenshotProcessor... processors) {
    this.device = device;
    this.processors = asList(processors);
    this.logListenerCommandShell = new ScreenshotRequestListener();
  }

  // todo: -

  //
  private class ScreenshotRequestListener extends MultiLineReceiver {
    private boolean activated = false, cancelled = false;

    public ScreenshotRequestListener() {
      setTrimLine(false);
    }

    public void activate() {
      activated = true;
    }

    @Override
    public boolean isCancelled() {
      return cancelled;
    }

    @Override
    public void processNewLines(String[] lines) {
      if (!activated || cancelled) {
        return;
      }

      String mostRecentLogLine = lines[lines.length - 1];

      // whatever the earlier requests corresponded to, that stuff is gone
      takeScreenshotFor(mostRecentLogLine);
    }
  }

  private void takeScreenshotFor(String logLine) {
    //
  }
}
