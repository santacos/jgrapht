/*
 * Created on Jul 29, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org._3pq.jgrapht;

import org._3pq.jgrapht.graph.DefaultGraph;


class DirectedMultigraph extends DefaultGraph
    implements DirectedGraph {
    /**
     * @see DefaultGraph
     */
    public DirectedMultigraph( EdgeFactory ef ) {
        super( ef, true, true );
    }
}