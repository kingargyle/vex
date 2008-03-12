package org.eclipse.jst.jsf.facelet.ui.internal.validation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jst.jsf.core.internal.JSFCorePlugin;
import org.eclipse.jst.jsf.facelet.core.internal.facet.FaceletFacet;
import org.eclipse.jst.jsf.facelet.ui.internal.FaceletUiPlugin;
import org.eclipse.jst.jsf.validation.internal.IJSFViewValidator;
import org.eclipse.jst.jsf.validation.internal.JSFValidatorFactory;
import org.eclipse.jst.jsf.validation.internal.ValidationPreferences;
import org.eclipse.wst.validation.internal.core.ValidationException;
import org.eclipse.wst.validation.internal.operations.LocalizedMessage;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidationContext;
import org.eclipse.wst.validation.internal.provisional.core.IValidatorJob;

public class HTMLValidator implements IValidatorJob
{

    public ISchedulingRule getSchedulingRule(final IValidationContext helper)
    {
        // no rule...
        return null;
    }

    public IStatus validateInJob(final IValidationContext helper,
            final IReporter reporter) throws ValidationException
    {
        IStatus status = Status.OK_STATUS;
        try
        {
            validate(helper, reporter);
        }
        catch (final ValidationException e)
        {
            status = new Status(IStatus.ERROR, FaceletUiPlugin.PLUGIN_ID,
                    IStatus.ERROR, e.getLocalizedMessage(), e);
        }
        return status;

    }

    public void cleanup(final IReporter reporter)
    {
        // do nothing
    }

    public void validate(final IValidationContext helper,
            final IReporter reporter) throws ValidationException
    {
        final String[] uris = helper.getURIs();
        final IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
        if (uris.length > 0)
        {
            IFile currentFile = null;

            for (int i = 0; i < uris.length && !reporter.isCancelled(); i++)
            {
                currentFile = wsRoot.getFile(new Path(uris[i]));
                if (currentFile != null && currentFile.exists())
                {
                    if (shouldValidate(currentFile))
                    {
                        final int percent = (i * 100) / uris.length + 1;
                        final IMessage message = new LocalizedMessage(
                                IMessage.LOW_SEVERITY, percent + "% " + uris[i]);
                        reporter.displaySubtask(this, message);

                        validateFile(currentFile, reporter);
                    }
                }
            }
        }

    }

    private void validateFile(final IFile file, final IReporter reporter)
    {
        final IJSFViewValidator validator = JSFValidatorFactory
                .createDefaultXMLValidator();
        final ValidationPreferences prefs = new ValidationPreferences(
                JSFCorePlugin.getDefault().getPreferenceStore());
        prefs.load();

        final ValidationReporter jsfReporter = new ValidationReporter(this,
                reporter, file, prefs);
        validator.validateView(file, jsfReporter);
    }

    private boolean shouldValidate(final IFile model)
    {
        // TODO:
        return FaceletFacet.hasFacet(model.getProject());
    }
}
