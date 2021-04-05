package net.thumbtack.school.functional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FunctionsOperationsTest {
    FunctionsOperations f = new FunctionsOperations();
    private static final String STR = "there is something to do with";
    private static final String PERSION = "Constantinius";

    @Test
    public void testDo() {
        f.doFunctionsOperations();
    }

    @Test
    public void test5() {
        Person person = new Person(PERSION);
        Person person1 = f.create1.apply(PERSION);
        Person person2 = f.create.apply(PERSION);
        assertAll(() -> {
            Assert.assertEquals(person, person1);
            Assert.assertEquals(person, person2);
        });
    }

    @Test
    public void testGetMothersMotherFather() {
        Person eve = new Person("Eve");
        Person adam = new Person("Adam");
        Person personGen1F = new Person("p", eve, adam);
        Person personGen1M = new Person("p", eve, adam);
        Person personGen2F = new Person("p", personGen1F, personGen1M);
        Person personGen2M = new Person("p", personGen1F, personGen1M);

        assertAll(() -> {
            Assert.assertEquals(adam, f.getMothersMotherFather(personGen2F));
            Assert.assertNull(f.getMothersMotherFather(personGen1F));
            Assert.assertNull(f.getMothersMotherFather(null));
        });
    }

    @Test
    public void testGetMothersMotherFatherOptional() {
        PersonOp eve = new PersonOp("Eve", null, null);
        PersonOp adam = new PersonOp("Adam", null, null);
        PersonOp personGen1F = new PersonOp("p", eve, adam);
        PersonOp personGen1M = new PersonOp("p", eve, adam);
        PersonOp personGen2F = new PersonOp("p", personGen1F, personGen1M);
        PersonOp personGen2M = new PersonOp("p", personGen1F, personGen1M);

        //Assert.assertEquals(Optional.ofNullable(adam), f.getMothersMotherFatherOptional(personGen2F));
        //Assert.assertNull(f.getMothersMotherFatherOptional(null));
        Assert.assertEquals(Optional.ofNullable(adam), f.getMothersMotherFatherOptional.apply(personGen2F));
        Assert.assertEquals(Optional.ofNullable(null),f.getMothersMotherFatherOptional.apply(personGen1F));
        Assert.assertEquals(Optional.ofNullable(null),f.getMothersMotherFatherOptional.apply(null));
    }
}