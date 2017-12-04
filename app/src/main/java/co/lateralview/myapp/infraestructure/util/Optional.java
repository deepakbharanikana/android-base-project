package co.lateralview.myapp.infraestructure.util;

import java.util.NoSuchElementException;

/**
 * Created by ezequielfernandezexcoffon on 12/2/17.
 */

public class Optional<M>
{
    private final M optional;

    public Optional(M optional)
    {
        this.optional = optional;
    }

    public boolean isEmpty()
    {
        return this.optional == null;
    }

    public M get()
    {
        if (optional == null)
        {
            throw new NoSuchElementException("No value present");
        }
        return optional;
    }
}
