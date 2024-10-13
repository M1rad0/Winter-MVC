package mg.itu.framework.objects;

import java.lang.reflect.Method;

public class VerbMethod {
    String verb;
    Method toExecute;
    
    public String getVerb() {
        return verb;
    }
    public void setVerb(String verb) {
        this.verb = verb;
    }
    public Method getToExecute() {
        return toExecute;
    }
    public void setToExecute(Method toExecute) {
        this.toExecute = toExecute;
    }
    public VerbMethod(String verb, Method toExecute) {
        this.verb = verb;
        this.toExecute = toExecute;
    }
     
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((verb == null) ? 0 : verb.hashCode());
        result = prime * result + ((toExecute == null) ? 0 : toExecute.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VerbMethod other = (VerbMethod) obj;
        if(verb.equals(other.getVerb())) return true;
        if(toExecute.equals(other.getToExecute())) return true;

        return false;
    }
}
