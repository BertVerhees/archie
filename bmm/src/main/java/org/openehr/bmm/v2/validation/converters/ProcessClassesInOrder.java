package org.openehr.bmm.v2.validation.converters;

import org.openehr.bmm.v2.persistence.PBmmClass;
import org.openehr.bmm.v2.persistence.PBmmGenericParameter;
import org.openehr.bmm.v2.persistence.PBmmSchema;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;

public class ProcessClassesInOrder {

    /**
     * Do some action to all primitive type and class objects
     * process in breadth first order of inheritance tree
     *
     * @param action
     * @param classesToProcess
     */
    public void doAllClassesInOrder(PBmmSchema schema, Consumer<PBmmClass> action, List<PBmmClass> classesToProcess) {
        int attempts = schema.getClassDefinitions().size() * 10;
        int tries = 0;
        List<String> visitedClasses = new ArrayList<>();
        Queue<PBmmClass> queue = new LinkedList<>();
        //Initial queue population
        for (PBmmClass bmmClass : classesToProcess) {
            processClass(schema, action, visitedClasses, queue, bmmClass);
        }
        //Go through the queue and remove nodes whose ancestors have already been processed
        while (!queue.isEmpty() && tries < attempts) {
            PBmmClass element = queue.remove();
            if (element == null) {

            } else {
                processClass(schema, action, visitedClasses, queue, element);
            }
            tries++;
        }
    }

    private void processClass(PBmmSchema schema, Consumer<PBmmClass> action, List<String> visitedClasses, Queue<PBmmClass> queue, PBmmClass bmmClass) {
        if (!visitedClasses.contains(bmmClass.getName().toUpperCase())) {
            boolean allAncestorsAndDependenciesVisited = true;
            for (String ancestor : bmmClass.getAncestors()) {
                if (!visitedClasses.contains(ancestor.toUpperCase())) {
                    allAncestorsAndDependenciesVisited = false;
                    PBmmClass ancestorDef = schema.findClassOrPrimitiveDefinition(ancestor);
                    queue.add(ancestorDef);
                }

            }
            if (bmmClass.isGeneric()) {
                Map<String, PBmmGenericParameter> parameters = bmmClass.getGenericParameterDefs();
                for (PBmmGenericParameter parameter : parameters.values()) {
                    String conformsTo = parameter.getConformsToType();
                    if (conformsTo != null && !visitedClasses.contains(conformsTo.toUpperCase())) {
                        allAncestorsAndDependenciesVisited = false;
                        PBmmClass dependency = schema.findClassOrPrimitiveDefinition(conformsTo);
                        queue.add(dependency);
                    }
                }
            }
            if (!allAncestorsAndDependenciesVisited) {
                queue.add(bmmClass);
            } else {
                action.accept(bmmClass);
                visitedClasses.add(bmmClass.getName().toUpperCase());
            }
        }
    }
}
