
package net.certware.argument.aml.components;

// Start of user code for imports
import net.certware.argument.aml.AmlPackage;
import net.certware.argument.aml.Value;
import net.certware.argument.aml.parts.AmlViewsRepository;
import net.certware.argument.aml.parts.ValuePropertiesEditionPart;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.EEFRuntimePlugin;
import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionListener;
import org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.api.providers.IPropertiesEditionPartProvider;
import org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesValidationEditionEvent;
import org.eclipse.emf.eef.runtime.impl.services.PropertiesEditionPartProviderService;
import org.eclipse.emf.eef.runtime.impl.utils.EEFConverterUtil;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
	

// End of user code

/**
 * @author mrb
 * 
 */
public class ValuePropertiesEditionComponent extends StandardPropertiesEditionComponent {

	
	public static String BASE_PART = "Base"; //$NON-NLS-1$

	
	private String[] parts = {BASE_PART};

	/**
	 * The EObject to edit
	 * 
	 */
	private Value value;

	/**
	 * The Base part
	 * 
	 */
	protected ValuePropertiesEditionPart basePart;

	/**
	 * Default constructor
	 * 
	 */
	public ValuePropertiesEditionComponent(EObject value, String editing_mode) {
		if (value instanceof Value) {
			this.value = (Value)value;
			if (IPropertiesEditionComponent.LIVE_MODE.equals(editing_mode)) {
				semanticAdapter = initializeSemanticAdapter();
				this.value.eAdapters().add(semanticAdapter);
			}
		}
		this.editing_mode = editing_mode;
	}

	/**
	 * Initialize the semantic model listener for live editing mode
	 * 
	 * @return the semantic model listener
	 * 
	 */
	private AdapterImpl initializeSemanticAdapter() {
		return new EContentAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
			 * 
			 */
			public void notifyChanged(final Notification msg) {
				if (basePart == null)
					ValuePropertiesEditionComponent.this.dispose();
				else {
					Runnable updateRunnable = new Runnable() {
						public void run() {
							runUpdateRunnable(msg);
						}
					};
					if (null == Display.getCurrent()) {
						PlatformUI.getWorkbench().getDisplay().syncExec(updateRunnable);
					} else {
						updateRunnable.run();
					}
				}
			}

		};
	}

	/**
	 * Used to update the views
	 * 
	 */
	protected void runUpdateRunnable(final Notification msg) {
		if (AmlPackage.eINSTANCE.getValue_Mixed().equals(msg.getFeature()) && basePart != null) {
			if (msg.getEventType() == Notification.ADD) 
				basePart.addToMixed((org.eclipse.emf.ecore.util.FeatureMap.Entry) msg.getNewValue());
			else if (msg.getEventType() == Notification.REMOVE) 
				basePart.removeToMixed((org.eclipse.emf.ecore.util.FeatureMap.Entry) msg.getNewValue());
		}

		if (AmlPackage.eINSTANCE.getValue_Type().equals(msg.getFeature()) && basePart != null){
			if (msg.getNewValue() != null) {
				basePart.setType(EcoreUtil.convertToString(XMLTypePackage.eINSTANCE.getString(), msg.getNewValue()));
			} else {
				basePart.setType("");
			}
		}
		if (AmlPackage.eINSTANCE.getValue_Unit().equals(msg.getFeature()) && basePart != null){
			if (msg.getNewValue() != null) {
				basePart.setUnit(EcoreUtil.convertToString(XMLTypePackage.eINSTANCE.getString(), msg.getNewValue()));
			} else {
				basePart.setUnit("");
			}
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#translatePart(java.lang.String)
	 * 
	 */
	public java.lang.Class translatePart(String key) {
		if (BASE_PART.equals(key))
			return AmlViewsRepository.Value.class;
		return super.translatePart(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#partsList()
	 * 
	 */
	public String[] partsList() {
		return parts;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#getPropertiesEditionPart
	 *  (java.lang.String, java.lang.String)
	 * 
	 */
	public IPropertiesEditionPart getPropertiesEditionPart(int kind, String key) {
		if (value != null && BASE_PART.equals(key)) {
			if (basePart == null) {
				IPropertiesEditionPartProvider provider = PropertiesEditionPartProviderService.getInstance().getProvider(AmlViewsRepository.class);
				if (provider != null) {
					basePart = (ValuePropertiesEditionPart)provider.getPropertiesEditionPart(AmlViewsRepository.Value.class, kind, this);
					addListener((IPropertiesEditionListener)basePart);
				}
			}
			return (IPropertiesEditionPart)basePart;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#
	 *      setPropertiesEditionPart(java.lang.Class, int, org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart)
	 * 
	 */
	public void setPropertiesEditionPart(java.lang.Class key, int kind, IPropertiesEditionPart propertiesEditionPart) {
		if (key == AmlViewsRepository.Value.class)
			this.basePart = (ValuePropertiesEditionPart) propertiesEditionPart;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#initPart(java.lang.Class, int, org.eclipse.emf.ecore.EObject, 
	 *      org.eclipse.emf.ecore.resource.ResourceSet)
	 * 
	 */
	public void initPart(java.lang.Class key, int kind, EObject elt, ResourceSet allResource) {
		setInitializing(true);
		if (basePart != null && key == AmlViewsRepository.Value.class) {
			((IPropertiesEditionPart)basePart).setContext(elt, allResource);
			final Value value = (Value)elt;
			// init values
			if (value.getMixed() != null)
				basePart.setMixed(new BasicEList(value.getMixed()));

			if (value.getType() != null)
				basePart.setType(EEFConverterUtil.convertToString(XMLTypePackage.eINSTANCE.getString(), value.getType()));

			if (value.getUnit() != null)
				basePart.setUnit(EEFConverterUtil.convertToString(XMLTypePackage.eINSTANCE.getString(), value.getUnit()));

			// init filters



		}
		// init values for referenced views

		// init filters for referenced views

		setInitializing(false);
	}






	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#getPropertiesEditionCommand
	 *     (org.eclipse.emf.edit.domain.EditingDomain)
	 * 
	 */
	public CompoundCommand getPropertiesEditionCommand(EditingDomain editingDomain) {
		CompoundCommand cc = new CompoundCommand();
		if ((value != null) && (basePart != null)) { 
//FIXME invalid case in commandUpdater(), Case : model = Attribute(*) : mixed - view = MultiValuedEditor

			cc.append(SetCommand.create(editingDomain, value, AmlPackage.eINSTANCE.getValue_Type(), EEFConverterUtil.createFromString(XMLTypePackage.eINSTANCE.getString(), basePart.getType())));
			cc.append(SetCommand.create(editingDomain, value, AmlPackage.eINSTANCE.getValue_Unit(), EEFConverterUtil.createFromString(XMLTypePackage.eINSTANCE.getString(), basePart.getUnit())));

		}
		if (!cc.isEmpty())
			return cc;
		cc.append(IdentityCommand.INSTANCE);
		return cc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#getPropertiesEditionObject()
	 * 
	 */
	public EObject getPropertiesEditionObject(EObject source) {
		if (source instanceof Value) {
			Value valueToUpdate = (Value)source;
//FIXME invalid case in partUpdater(), Case : model = Attribute(*) : mixed - view = MultiValuedEditor

			valueToUpdate.setType((java.lang.String)EEFConverterUtil.createFromString(XMLTypePackage.eINSTANCE.getString(), basePart.getType()));

			valueToUpdate.setUnit((java.lang.String)EEFConverterUtil.createFromString(XMLTypePackage.eINSTANCE.getString(), basePart.getUnit()));


			return valueToUpdate;
		}
		else
			return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionListener#firePropertiesChanged(org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent)
	 * 
	 */
	public void firePropertiesChanged(IPropertiesEditionEvent event) {
		if (!isInitializing()) {
			Diagnostic valueDiagnostic = validateValue(event);
			if (PropertiesEditionEvent.COMMIT == event.getState() && IPropertiesEditionComponent.LIVE_MODE.equals(editing_mode) && valueDiagnostic.getSeverity() == Diagnostic.OK) {
				CompoundCommand command = new CompoundCommand();
//FIXME invalid case in liveCommandUpdater(), Case : model = Attribute(*) : mixed - view = MultiValuedEditor

			if (AmlViewsRepository.Value.type == event.getAffectedEditor()) {
				command.append(SetCommand.create(liveEditingDomain, value, AmlPackage.eINSTANCE.getValue_Type(), EEFConverterUtil.createFromString(XMLTypePackage.eINSTANCE.getString(), (String)event.getNewValue())));
			}
			if (AmlViewsRepository.Value.unit == event.getAffectedEditor()) {
				command.append(SetCommand.create(liveEditingDomain, value, AmlPackage.eINSTANCE.getValue_Unit(), EEFConverterUtil.createFromString(XMLTypePackage.eINSTANCE.getString(), (String)event.getNewValue())));
			}

				if (!command.isEmpty() && !command.canExecute()) {
					EEFRuntimePlugin.getDefault().logError("Cannot perform model change command.", null);
				} else {
					liveEditingDomain.getCommandStack().execute(command);
				}
			}
			if (valueDiagnostic.getSeverity() != Diagnostic.OK && valueDiagnostic instanceof BasicDiagnostic)
				super.firePropertiesChanged(new PropertiesValidationEditionEvent(event, valueDiagnostic));
			else {
				Diagnostic validate = validate();
				super.firePropertiesChanged(new PropertiesValidationEditionEvent(event, validate));
			}
			super.firePropertiesChanged(event);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#validateValue(org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent)
	 * 
	 */
	public Diagnostic validateValue(IPropertiesEditionEvent event) {
		Diagnostic ret = Diagnostic.OK_INSTANCE;
		if (event.getNewValue() != null) {
			String newStringValue = event.getNewValue().toString();
			try {
				if (AmlViewsRepository.Value.mixed == event.getAffectedEditor()) {
					Object newValue = EcoreUtil.createFromString(AmlPackage.eINSTANCE.getValue_Mixed().getEAttributeType(), newStringValue);
					ret = Diagnostician.INSTANCE.validate(AmlPackage.eINSTANCE.getValue_Mixed().getEAttributeType(), newValue);
				}
				if (AmlViewsRepository.Value.type == event.getAffectedEditor()) {
					Object newValue = EcoreUtil.createFromString(AmlPackage.eINSTANCE.getValue_Type().getEAttributeType(), newStringValue);
					ret = Diagnostician.INSTANCE.validate(AmlPackage.eINSTANCE.getValue_Type().getEAttributeType(), newValue);
				}
				if (AmlViewsRepository.Value.unit == event.getAffectedEditor()) {
					Object newValue = EcoreUtil.createFromString(AmlPackage.eINSTANCE.getValue_Unit().getEAttributeType(), newStringValue);
					ret = Diagnostician.INSTANCE.validate(AmlPackage.eINSTANCE.getValue_Unit().getEAttributeType(), newValue);
				}
			} catch (IllegalArgumentException iae) {
				ret = BasicDiagnostic.toDiagnostic(iae);
			} catch (WrappedException we) {
				ret = BasicDiagnostic.toDiagnostic(we);
			}
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#validate()
	 * 
	 */
	public Diagnostic validate() {
		Diagnostic validate = Diagnostic.OK_INSTANCE;
		if (IPropertiesEditionComponent.BATCH_MODE.equals(editing_mode)) {
			EObject copy = EcoreUtil.copy(value);
			copy = getPropertiesEditionObject(copy);
			validate =  EEFRuntimePlugin.getEEFValidator().validate(copy);
		}
		else if (IPropertiesEditionComponent.LIVE_MODE.equals(editing_mode))
			validate = EEFRuntimePlugin.getEEFValidator().validate(value);
		// Start of user code for custom validation check
		
		// End of user code
		return validate;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#dispose()
	 * 
	 */
	public void dispose() {
		if (semanticAdapter != null)
			value.eAdapters().remove(semanticAdapter);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#getTabText(java.lang.String)
	 * 
	 */
	public String getTabText(String p_key) {
		return basePart.getTitle();
	}
}