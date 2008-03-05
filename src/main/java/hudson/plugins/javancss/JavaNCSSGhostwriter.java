package hudson.plugins.javancss;

import hudson.FilePath;
import hudson.plugins.helpers.Ghostwriter;
import hudson.plugins.helpers.BuildProxy;
import hudson.plugins.javancss.parser.Statistic;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.Action;

import java.io.IOException;
import java.io.File;
import java.util.Collection;

import org.xmlpull.v1.XmlPullParserException;

/**
 * TODO javadoc.
 *
 * @author Stephen Connolly
 * @since 08-Jan-2008 23:16:52
 */
public class JavaNCSSGhostwriter
        implements Ghostwriter,
        Ghostwriter.MasterGhostwriter,
        Ghostwriter.SlaveGhostwriter {

    private final String reportFilenamePattern;

    public JavaNCSSGhostwriter(String reportFilenamePattern) {
        this.reportFilenamePattern = reportFilenamePattern;
    }

    public boolean performFromMaster(AbstractBuild<?, ?> build, FilePath executionRoot, BuildListener listener) throws InterruptedException, IOException {
        return true;
    }

    public boolean performFromSlave(BuildProxy build, BuildListener listener) throws InterruptedException, IOException {
        FilePath[] paths = build.getExecutionRootDir().list(reportFilenamePattern);
        Collection<Statistic> results = null;
        for (FilePath path : paths) {
            try {
                Collection<Statistic> result = Statistic.parse(new File(path.getRemote()));
                if (results == null) {
                    results = result;
                } else {
                    results = Statistic.merge(results, result);
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace(listener.getLogger());
            }
        }
        if (results != null) {
            JavaNCSSBuildIndividualReport action = new JavaNCSSBuildIndividualReport(results);
            build.getActions().add(action);
        }
        return true;
    }
}