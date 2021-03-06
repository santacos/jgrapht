/* ==========================================
 * JGraphT : a free Java graph-theory library
 * ==========================================
 *
 * Project Info:  http://jgrapht.sourceforge.net/
 * Project Creator:  Barak Naveh (http://sourceforge.net/users/barak_naveh)
 *
 * (C) Copyright 2003-2009, by Barak Naveh and Contributors.
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
/* ----------------
 * GraphWalk.java
 * ----------------
 * (C) Copyright 2009-2009, by John V. Sichi and Contributors.
 *
 * Original Author:  John V. Sichi
 * Contributor(s):   Joris Kinable
 *
 * $Id$
 *
 * Changes
 * -------
 * 03-Jul-2009 : Initial revision (JVS);
 * 14-Jul-2016 : Redefined class to a more generic version of a walk, expressed in terms of its edges or vertices (JK)
 *
 */
package org.jgrapht.graph;

import java.util.*;

import org.jgrapht.*;


/**
 * A walk in a graph is an alternating sequence of vertices and edges, starting and ending at a vertex, in which each edge
 * is adjacent in the sequence to its two endpoints. More precisely, a walk is a connected sequence of vertices and edges
 * in a graph <code>v0, e0, v1, e1, v2,....vk-1, ek-1, vk</code>, such that for <code>1<=i<=k</code>, the edge <code>e_i</code>
 * has endpoints <code>v_(i-1)</code> and <code>v_i</code>. The class makes no assumptions with respect to the shape of the walk:
 * edges may be repeated, and the start and end point of the walk may be different.
 * @see <a href="http://mathworld.wolfram.com/Walk.html">http://mathworld.wolfram.com/Walk.html</a>
 *
 * GraphWalk is the default implementation of {@link GraphPath}.
 *
 * This class is implemented as a light-weight data structure; this class does not verify whether the sequence of edges
 * or the sequence of vertices provided during construction forms an actual walk. It is the responsibility of the invoking
 * class to provide correct input data.
 *
 * @author John Sichi
 * @version $Id$
 */
public class GraphWalk<V, E> implements GraphPath<V, E>
{
    protected Graph<V, E> graph;

    protected List<V> vertexList;
    protected List<E> edgeList;

    protected V startVertex;

    protected V endVertex;

    protected double weight;

    /**
     * Creates a walk defined by a sequence of edges. A walk defined by its edges can be specified for non-simple graphs.
     * Edge repetition is permitted, the start and end point points (v0 and vk) can be different.
     *
     * @param graph
     * @param startVertex
     * @param endVertex
     * @param edgeList
     * @param weight
     */
    public GraphWalk(
            Graph<V, E> graph,
            V startVertex,
            V endVertex,
            List<E> edgeList,
            double weight)
    {
        this(graph, startVertex, endVertex, null, edgeList, weight);
    }

    /**
     * Creates a walk defined by a sequence of vertices. Note that the input graph must be simple, otherwise
     * the vertex sequence does not necessarily define a unique path. Furthermore, all vertices must be pairwise adjacent.
     * @param graph
     * @param vertexList
     * @param weight
     */
    public GraphWalk(
            Graph<V, E> graph,
            List<V> vertexList,
            double weight)
    {
        this(graph,
                (vertexList.isEmpty() ? null : vertexList.get(0)),
                (vertexList.isEmpty() ? null : vertexList.get(vertexList.size()-1)),
                vertexList,
                null,
                weight);
    }

    /**
     * Creates a walk defined by both a sequence of edges and a sequence of vertices. Note that both the sequence of edges
     * and the sequence of vertices must describe the same path! This is not verified during the construction of the walk.
     * This constructor makes it possible to store both a vertex and an edge view of the same walk, thereby saving computational
     * overhead when switching from one to the other.
     * @param graph
     * @param startVertex
     * @param endVertex
     * @param vertexList
     * @param edgeList
     * @param weight
     */
    public GraphWalk(
            Graph<V, E> graph,
            V startVertex,
            V endVertex,
            List<V> vertexList,
            List<E> edgeList,
            double weight)
    {
        if(vertexList == null && edgeList == null)
            throw new IllegalArgumentException("Vertex list and edge list cannot both be null!");

        this.graph = graph;
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.vertexList=vertexList;
        this.edgeList = edgeList;
        this.weight = weight;
    }

    // implement GraphPath
    @Override public Graph<V, E> getGraph()
    {
        return graph;
    }

    // implement GraphPath
    @Override public V getStartVertex()
    {
        return startVertex;
    }

    // implement GraphPath
    @Override public V getEndVertex()
    {
        return endVertex;
    }

    // implement GraphPath
    @Override public List<E> getEdgeList()
    {
        return (edgeList != null ? edgeList : GraphPath.super.getEdgeList());
    }

    // implement GraphPath
    @Override public List<V> getVertexList()
    {
        return (vertexList != null ? vertexList : GraphPath.super.getVertexList());
    }

    // implement GraphPath
    @Override public double getWeight()
    {
        return weight;
    }

    // implement GraphPath
    @Override public int getLength()
    {
        if(edgeList!=null)
            return edgeList.size();
        else if(vertexList != null && !vertexList.isEmpty())
            return vertexList.size()-1;
        else
            return 0;
    }

    // override Object
    @Override public String toString()
    {
        if(vertexList!=null)
            return vertexList.toString();
        else
            return edgeList.toString();
    }
}

// End GraphPathImpl.java
