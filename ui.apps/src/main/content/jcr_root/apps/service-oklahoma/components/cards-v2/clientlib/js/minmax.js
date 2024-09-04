
(function($, $document, ns) {
  var GLOBALS = {
    latestEvent: ''
  };
  setupDialogEvents.call(this);
  $(window)
    .adaptTo("foundation-registry")
    .register("foundation.validation.validator", {
      selector: "[data-service-oklahoma-multifield-min-max]",
      validate: validate,
      show: show
    });

  // private functions
  function validate(el) {
    var validatorConfig = $(el).data("service-oklahomaMultifieldMinMax");
    if (el.items.length < validatorConfig.min &&
    GLOBALS.latestEvent === 'click') {
      // reset latestEvent;
      GLOBALS.latestEvent = '';
      return validatorConfig.minErrorMessage;
    } else if (el.items.length > validatorConfig.max) {
      return validatorConfig.maxErrorMessage;
    }
  }
  function show(el, message, ctx) {
    var validatorConfig = $(el).data("service-oklahomaMultifieldMinMax");
    if (message === validatorConfig.minErrorMessage) {
      showErrorAlertWithMessage.call(this, message);
    }
    if (message === validatorConfig.maxErrorMessage) {
      showErrorAlertWithMessage.call(this, message);
      var allMultifields = el.items.getAll();
      el.items.remove(allMultifields[allMultifields.length - 1]);
    }
  }
  // private functions
  function setupDialogEvents() {
    $(document)
      .on('coral-collection:add', 'coral-multifield[data-service-oklahoma-multifield-min-max]', collectionAddOrFormSubmitEvtHandler)
      .on('click', 'button.cq-dialog-submit', collectionAddOrFormSubmitEvtHandler)
      .on('dialog-closed', dialogClosedCallback);
  }

  function showErrorAlertWithMessage(message) {
    $(window)
      .adaptTo("foundation-ui")
      .alert("Alert", message, "error");
  }

  function collectionAddOrFormSubmitEvtHandler(evt) {
    GLOBALS.latestEvent = evt.type;
  }

  function dialogClosedCallback(evt) {
    GLOBALS.latestEvent = "";
  }

})(jQuery, jQuery(document), Granite.author);