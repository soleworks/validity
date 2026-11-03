# Add a message

1. Pick the class: `Messages` for a general constraint, `<Country>Messages` for a country, the module's
   class for a module ([conventions.md](../conventions.md)).
2. Add the property, named after the constraint (or after the rule, when several constraints share
   it), with its KDoc and its text ([conventions.md](../conventions.md#the-text)).
3. The constraint's test that reports with the default message covers the text. A new messages class
   of a module also gets a test of its own, like `PhoneMessagesTest`.
