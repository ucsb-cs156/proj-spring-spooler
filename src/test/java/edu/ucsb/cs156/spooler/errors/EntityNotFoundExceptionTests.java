package edu.ucsb.cs156.spooler.errors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EntityNotFoundExceptionTests {

    @Test
    public void testEntityNotFoundExceptionWithId() {
        EntityNotFoundException exception = new EntityNotFoundException(Object.class, Integer.valueOf(1));
        assertEquals("Object with id 1 not found", exception.getMessage());
    }


    @Test
    public void testEntityNotFoundExceptionWithTwoFields() {
        EntityNotFoundException exception = new EntityNotFoundException(Object.class, "Field1",Integer.valueOf(1), "Field2", Integer.valueOf(2));
        assertEquals("Object with Field1 1 and Field2 2 not found", exception.getMessage());
    }

}
