// Copyright (c) 2013 United States Government as represented by the National Aeronautics and Space Administration.  All rights reserved.
package net.certware.sacm.SACM.Evidence.components;

// Start of user code for imports
import net.certware.sacm.SACM.SACMPackage;
import net.certware.sacm.SACM.Evidence.CustodyProperty;
import net.certware.sacm.SACM.Evidence.EvidenceEvent;
import net.certware.sacm.SACM.Evidence.EvidencePackage;
import net.certware.sacm.SACM.Evidence.Provenance;
import net.certware.sacm.SACM.Evidence.TimingProperty;
import net.certware.sacm.SACM.Evidence.parts.EvidenceViewsRepository;
import net.certware.sacm.SACM.Evidence.parts.NotesPropertiesEditionPart;
import net.certware.sacm.SACM.Evidence.parts.ObjectPropertiesEditionPart;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.api.notify.EStructuralFeatureNotificationFilter;
import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.api.notify.NotificationFilter;
import org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditionContext;
import org.eclipse.emf.eef.runtime.context.impl.EReferencePropertiesEditionContext;
import org.eclipse.emf.eef.runtime.impl.components.SinglePartPropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.impl.utils.EEFConverterUtil;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.impl.CreateEditingPolicy;
import org.eclipse.emf.eef.runtime.providers.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.ui.widgets.ButtonsModeEnum;
import org.eclipse.emf.eef.runtime.ui.widgets.eobjflatcombo.EObjectFlatComboSettings;
import org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;


// End of user code

/**
 * @author Kestrel Technology LLC
 * 
 */
public class ObjectPropertiesEditionComponent extends SinglePartPropertiesEditingComponent {

	
	/**
   * The Base part
   * 
   */
  private ObjectPropertiesEditionPart basePart;


  /**
   * The ObjectBasePropertiesEditionComponent sub component
   * 
   */
  protected ObjectBasePropertiesEditionComponent objectBasePropertiesEditionComponent;


  /**
   * The Notes part
   * 
   */
  private NotesPropertiesEditionPart notesPart;


  /**
   * The ObjectNotesPropertiesEditionComponent sub component
   * 
   */
  protected ObjectNotesPropertiesEditionComponent objectNotesPropertiesEditionComponent;


  public static String BASE_PART = "Base"; //$NON-NLS-1$

	
	/**
	 * Settings for taggedValue ReferencesTable
	 */
	protected ReferencesTableSettings taggedValueSettings;
	
	/**
	 * Settings for annotation ReferencesTable
	 */
	protected ReferencesTableSettings annotationSettings;
	
	/**
	 * Settings for timing ReferencesTable
	 */
	protected ReferencesTableSettings timingSettings;
	
	/**
	 * Settings for custody ReferencesTable
	 */
	protected ReferencesTableSettings custodySettings;
	
	/**
	 * Settings for provenance ReferencesTable
	 */
	protected ReferencesTableSettings provenanceSettings;
	
	/**
	 * Settings for event ReferencesTable
	 */
	protected ReferencesTableSettings eventSettings;
	
	/**
	 * Settings for definition EObjectFlatComboViewer
	 */
	private EObjectFlatComboSettings definitionSettings;
	
	
	/**
	 * Default constructor
	 * 
	 */
	public ObjectPropertiesEditionComponent(PropertiesEditingContext editingContext, EObject object, String editing_mode) {
		super(editingContext, object, editing_mode);
		parts = new String[] { BASE_PART };
		repositoryKey = EvidenceViewsRepository.class;
		partKey = EvidenceViewsRepository.Object.class;
	}

	/**
   * {@inheritDoc}
   * 
   * @see org.eclipse.emf.eef.runtime.impl.components.ComposedPropertiesEditionComponent#
   *      getPropertiesEditionPart(int, java.lang.String)
   * 
   */
  public IPropertiesEditionPart getPropertiesEditionPart(int kind, String key) {
    if (ObjectBasePropertiesEditionComponent.BASE_PART.equals(key)) {
      basePart = (ObjectPropertiesEditionPart)objectBasePropertiesEditionComponent.getPropertiesEditionPart(kind, key);
      return (IPropertiesEditionPart)basePart;
    }
    if (ObjectNotesPropertiesEditionComponent.NOTES_PART.equals(key)) {
      notesPart = (NotesPropertiesEditionPart)objectNotesPropertiesEditionComponent.getPropertiesEditionPart(kind, key);
      return (IPropertiesEditionPart)notesPart;
    }
    return super.getPropertiesEditionPart(kind, key);
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.emf.eef.runtime.impl.components.ComposedPropertiesEditionComponent#
   *      setPropertiesEditionPart(java.lang.Object, int,
   *      org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart)
   * 
   */
  public void setPropertiesEditionPart(java.lang.Object key, int kind, IPropertiesEditionPart propertiesEditionPart) {
    if (EvidenceViewsRepository.Object.class == key) {
      super.setPropertiesEditionPart(key, kind, propertiesEditionPart);
      basePart = (ObjectPropertiesEditionPart)propertiesEditionPart;
    }
    if (EvidenceViewsRepository.Notes.class == key) {
      super.setPropertiesEditionPart(key, kind, propertiesEditionPart);
      notesPart = (NotesPropertiesEditionPart)propertiesEditionPart;
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.emf.eef.runtime.impl.components.ComposedPropertiesEditionComponent#
   *      initPart(java.lang.Object, int, org.eclipse.emf.ecore.EObject,
   *      org.eclipse.emf.ecore.resource.ResourceSet)
   * 
   */
  /*
  public void initPart(java.lang.Object key, int kind, EObject element, ResourceSet allResource) {
    if (key == EvidenceViewsRepository.Object.class) {
      super.initPart(key, kind, element, allResource);
    }
    if (key == EvidenceViewsRepository.Notes.class) {
      super.initPart(key, kind, element, allResource);
    }
  }
*/

  /**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#initPart(java.lang.Object, int, org.eclipse.emf.ecore.EObject, 
	 *      org.eclipse.emf.ecore.resource.ResourceSet)
	 * @generated
	 */
	public void initPart(Object key, int kind, EObject elt, ResourceSet allResource) {
		setInitializing(true);
		if (editingPart != null && key == partKey) {
			editingPart.setContext(elt, allResource);
			
			// TODO generated fails: final Object object = (Object)elt;
			net.certware.sacm.SACM.Evidence.Object object = (net.certware.sacm.SACM.Evidence.Object) elt;
					
			final ObjectPropertiesEditionPart basePart = (ObjectPropertiesEditionPart)editingPart;
			if (isAccessible(EvidenceViewsRepository.Object.Properties.id))
				basePart.setId(EEFConverterUtil.convertToString(SACMPackage.Literals.STRING, ((ObjectPropertiesEditionPart) object).getId()));
			
			if (isAccessible(EvidenceViewsRepository.Object.Properties.timing)) {
				timingSettings = new ReferencesTableSettings(object, EvidencePackage.eINSTANCE.getEvidenceElement_Timing());
				basePart.initTiming(timingSettings);
			}
			if (isAccessible(EvidenceViewsRepository.Object.Properties.custody)) {
				custodySettings = new ReferencesTableSettings(object, EvidencePackage.eINSTANCE.getEvidenceElement_Custody());
				basePart.initCustody(custodySettings);
			}
			if (isAccessible(EvidenceViewsRepository.Object.Properties.provenance)) {
				provenanceSettings = new ReferencesTableSettings(object, EvidencePackage.eINSTANCE.getEvidenceElement_Provenance());
				basePart.initProvenance(provenanceSettings);
			}
			if (isAccessible(EvidenceViewsRepository.Object.Properties.event)) {
				eventSettings = new ReferencesTableSettings(object, EvidencePackage.eINSTANCE.getEvidenceElement_Event());
				basePart.initEvent(eventSettings);
			}
			if (isAccessible(EvidenceViewsRepository.Object.Properties.name))
				basePart.setName(EEFConverterUtil.convertToString(SACMPackage.Literals.STRING, ((ObjectPropertiesEditionPart) object).getName()));
			
			if (isAccessible(EvidenceViewsRepository.Object.Properties.concept))
				basePart.setConcept(EEFConverterUtil.convertToString(SACMPackage.Literals.STRING, ((ObjectPropertiesEditionPart) object).getConcept()));
			
			if (isAccessible(EvidenceViewsRepository.Object.Properties.definition)) {
				// init part
				definitionSettings = new EObjectFlatComboSettings(object, EvidencePackage.eINSTANCE.getObject_Definition());
				basePart.initDefinition(definitionSettings);
				// set the button mode
				basePart.setDefinitionButtonMode(ButtonsModeEnum.BROWSE);
			}
			
			if (isAccessible(EvidenceViewsRepository.Object.Properties.timing)) {
				basePart.addFilterToTiming(new ViewerFilter() {
					/**
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
					 */
					public boolean select(Viewer viewer, Object parentElement, Object element) {
						return (element instanceof String && element.equals("")) || (element instanceof TimingProperty); //$NON-NLS-1$ 
					}
			
				});
				// Start of user code for additional businessfilters for timing
				// End of user code
			}
			if (isAccessible(EvidenceViewsRepository.Object.Properties.custody)) {
				basePart.addFilterToCustody(new ViewerFilter() {
					/**
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
					 */
					public boolean select(Viewer viewer, Object parentElement, Object element) {
						return (element instanceof String && element.equals("")) || (element instanceof CustodyProperty); //$NON-NLS-1$ 
					}
			
				});
				// Start of user code for additional businessfilters for custody
				// End of user code
			}
			if (isAccessible(EvidenceViewsRepository.Object.Properties.provenance)) {
				basePart.addFilterToProvenance(new ViewerFilter() {
					/**
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
					 */
					public boolean select(Viewer viewer, Object parentElement, Object element) {
						return (element instanceof String && element.equals("")) || (element instanceof Provenance); //$NON-NLS-1$ 
					}
			
				});
				// Start of user code for additional businessfilters for provenance
				// End of user code
			}
			if (isAccessible(EvidenceViewsRepository.Object.Properties.event)) {
				basePart.addFilterToEvent(new ViewerFilter() {
					/**
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
					 */
					public boolean select(Viewer viewer, Object parentElement, Object element) {
						return (element instanceof String && element.equals("")) || (element instanceof EvidenceEvent); //$NON-NLS-1$ 
					}
			
				});
				// Start of user code for additional businessfilters for event
				// End of user code
			}
			
			
			if (isAccessible(EvidenceViewsRepository.Object.Properties.definition)) {
				basePart.addFilterToDefinition(new ViewerFilter() {
				
					/**
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
					 */
					public boolean select(Viewer viewer, Object parentElement, Object element) {
						return (element instanceof EObject);
					}
					
				});
				// Start of user code for additional businessfilters for definition
				// End of user code
			}
			// init values for referenced views
			
			// init filters for referenced views
			
		}
		setInitializing(false);
	}













	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#associatedFeature(java.lang.Object)
	 */
	public EStructuralFeature associatedFeature(Object editorKey) {
		if (editorKey == EvidenceViewsRepository.Object.Properties.id) {
			return SACMPackage.eINSTANCE.getModelElement_Id();
		}
		if (editorKey == EvidenceViewsRepository.Object.Properties.timing) {
			return EvidencePackage.eINSTANCE.getEvidenceElement_Timing();
		}
		if (editorKey == EvidenceViewsRepository.Object.Properties.custody) {
			return EvidencePackage.eINSTANCE.getEvidenceElement_Custody();
		}
		if (editorKey == EvidenceViewsRepository.Object.Properties.provenance) {
			return EvidencePackage.eINSTANCE.getEvidenceElement_Provenance();
		}
		if (editorKey == EvidenceViewsRepository.Object.Properties.event) {
			return EvidencePackage.eINSTANCE.getEvidenceElement_Event();
		}
		if (editorKey == EvidenceViewsRepository.Object.Properties.name) {
			return EvidencePackage.eINSTANCE.getFormalObject_Name();
		}
		if (editorKey == EvidenceViewsRepository.Object.Properties.concept) {
			return EvidencePackage.eINSTANCE.getObject_Concept();
		}
		if (editorKey == EvidenceViewsRepository.Object.Properties.definition) {
			return EvidencePackage.eINSTANCE.getObject_Definition();
		}
		return super.associatedFeature(editorKey);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#updateSemanticModel(org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent)
	 * @generated
	 */
	public void updateSemanticModel(final IPropertiesEditionEvent event) {
		Object object = (Object)semanticObject;
		if (EvidenceViewsRepository.Object.Properties.id == event.getAffectedEditor()) {
			((ObjectPropertiesEditionPart) object).setId((java.lang.String)EEFConverterUtil.createFromString(SACMPackage.Literals.STRING, (String)event.getNewValue()));
		}
		if (EvidenceViewsRepository.Object.Properties.timing == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.ADD) {
				EReferencePropertiesEditionContext context = new EReferencePropertiesEditionContext(editingContext, this, timingSettings, editingContext.getAdapterFactory());
				PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(semanticObject, PropertiesEditingProvider.class);
				if (provider != null) {
					PropertiesEditingPolicy policy = provider.getPolicy(context);
					if (policy instanceof CreateEditingPolicy) {
						policy.execute();
					}
				}
			} else if (event.getKind() == PropertiesEditionEvent.EDIT) {
				EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(editingContext, this, (EObject) event.getNewValue(), editingContext.getAdapterFactory());
				PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt((EObject) event.getNewValue(), PropertiesEditingProvider.class);
				if (provider != null) {
					PropertiesEditingPolicy editionPolicy = provider.getPolicy(context);
					if (editionPolicy != null) {
						editionPolicy.execute();
					}
				}
			} else if (event.getKind() == PropertiesEditionEvent.REMOVE) {
				timingSettings.removeFromReference((EObject) event.getNewValue());
			} else if (event.getKind() == PropertiesEditionEvent.MOVE) {
				timingSettings.move(event.getNewIndex(), (TimingProperty) event.getNewValue());
			}
		}
		if (EvidenceViewsRepository.Object.Properties.custody == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.ADD) {
				EReferencePropertiesEditionContext context = new EReferencePropertiesEditionContext(editingContext, this, custodySettings, editingContext.getAdapterFactory());
				PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(semanticObject, PropertiesEditingProvider.class);
				if (provider != null) {
					PropertiesEditingPolicy policy = provider.getPolicy(context);
					if (policy instanceof CreateEditingPolicy) {
						policy.execute();
					}
				}
			} else if (event.getKind() == PropertiesEditionEvent.EDIT) {
				EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(editingContext, this, (EObject) event.getNewValue(), editingContext.getAdapterFactory());
				PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt((EObject) event.getNewValue(), PropertiesEditingProvider.class);
				if (provider != null) {
					PropertiesEditingPolicy editionPolicy = provider.getPolicy(context);
					if (editionPolicy != null) {
						editionPolicy.execute();
					}
				}
			} else if (event.getKind() == PropertiesEditionEvent.REMOVE) {
				custodySettings.removeFromReference((EObject) event.getNewValue());
			} else if (event.getKind() == PropertiesEditionEvent.MOVE) {
				custodySettings.move(event.getNewIndex(), (CustodyProperty) event.getNewValue());
			}
		}
		if (EvidenceViewsRepository.Object.Properties.provenance == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.ADD) {
				EReferencePropertiesEditionContext context = new EReferencePropertiesEditionContext(editingContext, this, provenanceSettings, editingContext.getAdapterFactory());
				PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(semanticObject, PropertiesEditingProvider.class);
				if (provider != null) {
					PropertiesEditingPolicy policy = provider.getPolicy(context);
					if (policy instanceof CreateEditingPolicy) {
						policy.execute();
					}
				}
			} else if (event.getKind() == PropertiesEditionEvent.EDIT) {
				EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(editingContext, this, (EObject) event.getNewValue(), editingContext.getAdapterFactory());
				PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt((EObject) event.getNewValue(), PropertiesEditingProvider.class);
				if (provider != null) {
					PropertiesEditingPolicy editionPolicy = provider.getPolicy(context);
					if (editionPolicy != null) {
						editionPolicy.execute();
					}
				}
			} else if (event.getKind() == PropertiesEditionEvent.REMOVE) {
				provenanceSettings.removeFromReference((EObject) event.getNewValue());
			} else if (event.getKind() == PropertiesEditionEvent.MOVE) {
				provenanceSettings.move(event.getNewIndex(), (Provenance) event.getNewValue());
			}
		}
		if (EvidenceViewsRepository.Object.Properties.event == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.ADD) {
				EReferencePropertiesEditionContext context = new EReferencePropertiesEditionContext(editingContext, this, eventSettings, editingContext.getAdapterFactory());
				PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(semanticObject, PropertiesEditingProvider.class);
				if (provider != null) {
					PropertiesEditingPolicy policy = provider.getPolicy(context);
					if (policy instanceof CreateEditingPolicy) {
						policy.execute();
					}
				}
			} else if (event.getKind() == PropertiesEditionEvent.EDIT) {
				EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(editingContext, this, (EObject) event.getNewValue(), editingContext.getAdapterFactory());
				PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt((EObject) event.getNewValue(), PropertiesEditingProvider.class);
				if (provider != null) {
					PropertiesEditingPolicy editionPolicy = provider.getPolicy(context);
					if (editionPolicy != null) {
						editionPolicy.execute();
					}
				}
			} else if (event.getKind() == PropertiesEditionEvent.REMOVE) {
				eventSettings.removeFromReference((EObject) event.getNewValue());
			} else if (event.getKind() == PropertiesEditionEvent.MOVE) {
				eventSettings.move(event.getNewIndex(), (EvidenceEvent) event.getNewValue());
			}
		}
		if (EvidenceViewsRepository.Object.Properties.name == event.getAffectedEditor()) {
			((ObjectPropertiesEditionPart) object).setName((java.lang.String)EEFConverterUtil.createFromString(SACMPackage.Literals.STRING, (String)event.getNewValue()));
		}
		if (EvidenceViewsRepository.Object.Properties.concept == event.getAffectedEditor()) {
			((ObjectPropertiesEditionPart) object).setConcept((java.lang.String)EEFConverterUtil.createFromString(SACMPackage.Literals.STRING, (String)event.getNewValue()));
		}
		if (EvidenceViewsRepository.Object.Properties.definition == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.SET) {
				definitionSettings.setToReference((EObject)event.getNewValue());
			} else if (event.getKind() == PropertiesEditionEvent.ADD) {
				EObject eObject = EcoreFactory.eINSTANCE.createEObject();
				EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(editingContext, this, eObject, editingContext.getAdapterFactory());
				PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(eObject, PropertiesEditingProvider.class);
				if (provider != null) {
					PropertiesEditingPolicy policy = provider.getPolicy(context);
					if (policy != null) {
						policy.execute();
					}
				}
				definitionSettings.setToReference(eObject);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#updatePart(org.eclipse.emf.common.notify.Notification)
	 */
	public void updatePart(Notification msg) {
		super.updatePart(msg);
		if (editingPart.isVisible()) {
			ObjectPropertiesEditionPart basePart = (ObjectPropertiesEditionPart)editingPart;
			if (SACMPackage.eINSTANCE.getModelElement_Id().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EvidenceViewsRepository.Object.Properties.id)) {
				if (msg.getNewValue() != null) {
					basePart.setId(EcoreUtil.convertToString(SACMPackage.Literals.STRING, msg.getNewValue()));
				} else {
					basePart.setId("");
				}
			}
			if (EvidencePackage.eINSTANCE.getEvidenceElement_Timing().equals(msg.getFeature()) && isAccessible(EvidenceViewsRepository.Object.Properties.timing))
				basePart.updateTiming();
			if (EvidencePackage.eINSTANCE.getEvidenceElement_Custody().equals(msg.getFeature()) && isAccessible(EvidenceViewsRepository.Object.Properties.custody))
				basePart.updateCustody();
			if (EvidencePackage.eINSTANCE.getEvidenceElement_Provenance().equals(msg.getFeature()) && isAccessible(EvidenceViewsRepository.Object.Properties.provenance))
				basePart.updateProvenance();
			if (EvidencePackage.eINSTANCE.getEvidenceElement_Event().equals(msg.getFeature()) && isAccessible(EvidenceViewsRepository.Object.Properties.event))
				basePart.updateEvent();
			if (EvidencePackage.eINSTANCE.getFormalObject_Name().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EvidenceViewsRepository.Object.Properties.name)) {
				if (msg.getNewValue() != null) {
					basePart.setName(EcoreUtil.convertToString(SACMPackage.Literals.STRING, msg.getNewValue()));
				} else {
					basePart.setName("");
				}
			}
			if (EvidencePackage.eINSTANCE.getObject_Concept().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EvidenceViewsRepository.Object.Properties.concept)) {
				if (msg.getNewValue() != null) {
					basePart.setConcept(EcoreUtil.convertToString(SACMPackage.Literals.STRING, msg.getNewValue()));
				} else {
					basePart.setConcept("");
				}
			}
			if (EvidencePackage.eINSTANCE.getObject_Definition().equals(msg.getFeature()) && basePart != null && isAccessible(EvidenceViewsRepository.Object.Properties.definition))
				basePart.setDefinition((EObject)msg.getNewValue());
			
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#getNotificationFilters()
	 */
	@Override
	protected NotificationFilter[] getNotificationFilters() {
		NotificationFilter filter = new EStructuralFeatureNotificationFilter(
			SACMPackage.eINSTANCE.getModelElement_TaggedValue(),
			SACMPackage.eINSTANCE.getModelElement_Annotation(),
			SACMPackage.eINSTANCE.getModelElement_Id(),
			EvidencePackage.eINSTANCE.getEvidenceElement_Timing(),
			EvidencePackage.eINSTANCE.getEvidenceElement_Custody(),
			EvidencePackage.eINSTANCE.getEvidenceElement_Provenance(),
			EvidencePackage.eINSTANCE.getEvidenceElement_Event(),
			EvidencePackage.eINSTANCE.getFormalObject_Name(),
			EvidencePackage.eINSTANCE.getObject_Concept(),
			EvidencePackage.eINSTANCE.getObject_Definition()		);
		return new NotificationFilter[] {filter,};
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#isRequired(java.lang.Object, int)
	 * 
	 */
	public boolean isRequired(Object key, int kind) {
		return key == EvidenceViewsRepository.Object.Properties.id || key == EvidenceViewsRepository.Object.Properties.name || key == EvidenceViewsRepository.Object.Properties.concept || key == EvidenceViewsRepository.Object.Properties.definition;
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
			try {
				if (EvidenceViewsRepository.Object.Properties.id == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(SACMPackage.eINSTANCE.getModelElement_Id().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(SACMPackage.eINSTANCE.getModelElement_Id().getEAttributeType(), newValue);
				}
				if (EvidenceViewsRepository.Object.Properties.name == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EvidencePackage.eINSTANCE.getFormalObject_Name().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EvidencePackage.eINSTANCE.getFormalObject_Name().getEAttributeType(), newValue);
				}
				if (EvidenceViewsRepository.Object.Properties.concept == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EvidencePackage.eINSTANCE.getObject_Concept().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EvidencePackage.eINSTANCE.getObject_Concept().getEAttributeType(), newValue);
				}
			} catch (IllegalArgumentException iae) {
				ret = BasicDiagnostic.toDiagnostic(iae);
			} catch (WrappedException we) {
				ret = BasicDiagnostic.toDiagnostic(we);
			}
		}
		return ret;
	}


	

}