package hudson.plugins.javancss;

import hudson.plugins.helpers.health.HealthTarget;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: stephen
 * Date: 18-Mar-2008
 * Time: 06:11:01
 * To change this template use File | Settings | File Templates.
 */
public class JavaNCSSHealthTarget extends HealthTarget<JavaNCSSHealthMetrics> {
    @DataBoundConstructor
    public JavaNCSSHealthTarget(JavaNCSSHealthMetrics healthMetric, String healthy, String unhealthy, String unstable) {
        super(healthMetric, healthy, unhealthy, unstable);
    }
}
