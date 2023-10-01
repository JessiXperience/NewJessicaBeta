package jessica.value;

import jessica.Wrapper;

public class ValueMode extends Value {
	private String name;
	private String[] values;
	private String value;
	
	public ValueMode(String name, String value, String[] values) {
		super(name, value);
		this.name = name;
		this.value = value;
		this.values = values;
		arrayVal.add(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	//for clickgui -> ModeElement
    public void increment() {
        String currentMode = getValue();

        for (String mode : getValues()) {
            if (!mode.equalsIgnoreCase(currentMode)) {
                continue;
            }

            String newValue;

            int ordinal = getOrdinal(mode, getValues());
            if (ordinal == getValues().length - 1) {
                newValue = getValues()[0];
            } else {
                newValue = getValues()[ordinal + 1];
            }

            setValue(newValue);
            Wrapper.getFiles().saveValues();
            return;
        }
    }

    public void decrement() {
        String currentMode = getValue();

        for (String mode : getValues()) {
            if (!mode.equalsIgnoreCase(currentMode)) {
                continue;
            }

            String newValue;

            int ordinal = getOrdinal(mode, getValues());
            if (ordinal == 0) {
                newValue = getValues()[getValues().length - 1];
            } else {
                newValue = getValues()[ordinal - 1];
            }

            setValue(newValue);
            Wrapper.getFiles().saveValues();
            return;
        }
    }

    private int getOrdinal(String value, String[] array) {
        for (int i = 0; i <= array.length - 1; i++) {
            String indexString = array[i];
            if (indexString.equalsIgnoreCase(value)) {
                return i;
            }
        }
        return 0;
    }

}
